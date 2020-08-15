package com.heshi.nacos.service;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.SystemPropertyKeyConst;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.filter.impl.ConfigFilterChainManager;
import com.alibaba.nacos.client.config.http.HttpAgent;
import com.alibaba.nacos.client.config.http.MetricsHttpAgent;
import com.alibaba.nacos.client.config.http.ServerHttpAgent;
import com.alibaba.nacos.client.config.impl.ClientWorker;
import com.alibaba.nacos.client.config.impl.HttpSimpleClient;
import com.alibaba.nacos.client.utils.LogUtils;
import com.alibaba.nacos.client.utils.TemplateUtils;
import com.alibaba.nacos.client.utils.TenantUtil;
import com.heshi.nacos.common.NacosConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * @Author: fukun
 * @Date: 2020/8/13 19:35
 * @since
 */
public class NacosNameSpaceService {

    private static final Logger LOGGER = LogUtils.logger(NacosNameSpaceService.class);
    private static final long POST_TIMEOUT = 3000L;
    /**
     * http agent
     */
    private HttpAgent agent;
    /**
     * longpolling
     */
    private ClientWorker worker;
    private String namespace;
    private String encode;
    private ConfigFilterChainManager configFilterChainManager = new ConfigFilterChainManager();

    public NacosNameSpaceService(Properties properties) throws NacosException {
        String encodeTmp = properties.getProperty(PropertyKeyConst.ENCODE);
        if (StringUtils.isBlank(encodeTmp)) {
            encode = Constants.ENCODE;
        } else {
            encode = encodeTmp.trim();
        }
        initNamespace(properties);
        agent = new MetricsHttpAgent(new ServerHttpAgent(properties));
        agent.start();
        worker = new ClientWorker(agent, configFilterChainManager, properties);
    }

    private void initNamespace(Properties properties) {
        namespace = parseNamespace(properties);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
    }

    /**
     * 发布namespace
     *
     * @param namespaceId
     * @param namespaceName
     * @param namespaceDesc
     * @return
     * @throws NacosException
     */
    public boolean publishNamespace(String namespaceId, String namespaceName, String namespaceDesc) throws NacosException {
        return publishNamespaceInner(namespaceId, namespaceName, namespaceDesc, false);
    }

    /**
     * 修改namespace
     *
     * @param namespaceId
     * @param namespaceName
     * @param namespaceDesc
     * @return
     */
//    public boolean updateNamespace(String namespaceId, String namespaceName, String namespaceDesc) throws NacosException {
//        String url = NacosConstants.NAMESPACE_CONTROLLER_PATH;
//        List<String> params = new ArrayList<String>();
//        params.add("namespace");
//        params.add(namespaceId);
//        params.add("namespaceShowName");
//        params.add(namespaceName);
//        params.add("namespaceDesc");
//        params.add(namespaceDesc);
//
//        List<String> headers = new ArrayList<String>();
//
//        HttpSimpleClient.HttpResult result = null;
//        try {
//            result = agent.httpPost(url, headers, params, encode, POST_TIMEOUT);
//        } catch (IOException ioe) {
//            LOGGER.warn("[{}] [publish-single] exception, customNamespaceId={}, namespaceName={}, namespaceDesc={}, msg={}", namespaceId, namespaceName,
//                    namespaceDesc, ioe.toString());
//            return false;
//        }
//        if (HttpURLConnection.HTTP_OK == result.code) {
//            LOGGER.info("[{}] [publish-single] ok, customNamespaceId={}, namespaceName={}, namespaceDesc={}", namespaceId, namespaceName,
//                    namespaceDesc);
//            return true;
//        } else if (HttpURLConnection.HTTP_FORBIDDEN == result.code) {
//            LOGGER.info("[{}] [publish-single] ok, customNamespaceId={}, namespaceName={}, namespaceDesc={},code={}, msg={}", namespaceId, namespaceName,
//                    namespaceDesc, result.code, result.content);
//            throw new NacosException(result.code, result.content);
//        } else {
//            LOGGER.info("[{}] [publish-single] ok, customNamespaceId={}, namespaceName={}, namespaceDesc={},code={}, msg={}", namespaceId, namespaceName,
//                    namespaceDesc, result.code, result.content);
//            return false;
//        }
//    }

    /**
     * 删除namespace
     *
     * @param namespaceId
     * @return
     */
    public boolean removeNamespace(String namespaceId) throws NacosException {
        String url = NacosConstants.NAMESPACE_CONTROLLER_PATH;
        List<String> params = new ArrayList<String>();
        params.add("namespaceId");
        params.add(namespaceId);
        HttpSimpleClient.HttpResult result = null;
        try {
            result = agent.httpDelete(url, null, params, encode, POST_TIMEOUT);
        } catch (IOException ioe) {
            LOGGER.warn("[remove] error, " + namespaceId + ", msg: " + ioe.toString());
            return false;
        }

        if (HttpURLConnection.HTTP_OK == result.code) {
            LOGGER.info("[{}] [remove] ok, namespaceId={}", agent.getName(), namespaceId);
            return true;
        } else if (HttpURLConnection.HTTP_FORBIDDEN == result.code) {
            LOGGER.warn("[{}] [remove] error, namespaceId={}, code={}, msg={}", agent.getName(), namespaceId,
                    result.code, result.content);
            throw new NacosException(result.code, result.content);
        } else {
            LOGGER.warn("[{}] [remove] error, namespaceId={}, code={}, msg={}", agent.getName(), namespaceId,
                    result.code, result.content);
            return false;
        }
    }

    /**
     * 查询namespace
     *
     * @param namespaceId
     * @param timeoutMs
     * @return
     * @throws NacosException
     */
    public String getNamespace(String namespaceId, long timeoutMs) throws NacosException {
        String url = NacosConstants.NAMESPACE_CONTROLLER_PATH;
        List<String> params = new ArrayList<String>();
        params.add("namespaceId");
        params.add(namespaceId);
        params.add("show");
        params.add("all");

        List<String> headers = new ArrayList<String>();

        HttpSimpleClient.HttpResult result = null;
        try {
            result = agent.httpGet(url, headers, params, encode, timeoutMs);
        } catch (IOException ioe) {
            String message = String.format(
                    "[%s] [sub-server] get server config exception, namespaceId=%s", agent.getName(), namespaceId);
            LOGGER.error(message, ioe);
            throw new NacosException(NacosException.SERVER_ERROR, ioe);
        }

        switch (result.code) {
            case HttpURLConnection.HTTP_OK:
                return result.content;
            case HttpURLConnection.HTTP_NOT_FOUND:
                return null;
            case HttpURLConnection.HTTP_CONFLICT: {
                throw new NacosException(NacosException.CONFLICT,
                        "data being modified, namespaceId=" + namespaceId);
            }
            case HttpURLConnection.HTTP_FORBIDDEN: {
                LOGGER.error("[{}] [sub-server-error] no right, namespaceId={}", agent.getName(), namespaceId);
                throw new NacosException(result.code, result.content);
            }
            default: {
                LOGGER.error("[{}] [sub-server-error]  namespaceId={}", agent.getName(), namespaceId, result.code);
                throw new NacosException(result.code,
                        "http error, code=" + result.code + ",namespaceId=" + namespaceId);
            }
        }
    }

    /**
     * 校验namespaceID是否存在
     *
     * @param namespaceId
     * @param namespaceName
     * @param namespaceDesc
     * @param checkNamespaceIdExist
     * @return
     * @throws NacosException
     */
    public boolean checkNamespaceIdExist(String namespaceId, String namespaceName, String namespaceDesc, Boolean checkNamespaceIdExist) throws NacosException {
        return publishNamespaceInner(namespaceId, namespaceName, namespaceDesc, checkNamespaceIdExist);
    }

    /**
     * 保存namespace
     *
     * @param namespaceId
     * @param namespaceName
     * @param namespaceDesc
     * @param checkNamespaceIdExist
     * @return
     * @throws NacosException
     */
    private boolean publishNamespaceInner(String namespaceId, String namespaceName, String namespaceDesc, Boolean checkNamespaceIdExist) throws NacosException {

        String url = NacosConstants.NAMESPACE_CONTROLLER_PATH;
        List<String> params = new ArrayList<String>();
        params.add("customNamespaceId");
        params.add(namespaceId);
        params.add("namespaceName");
        params.add(namespaceName);
        params.add("namespaceDesc");
        params.add(namespaceDesc);
        if (checkNamespaceIdExist) {
            params.add("checkNamespaceIdExist");
            params.add("true");
        }

        List<String> headers = new ArrayList<String>();

        HttpSimpleClient.HttpResult result = null;
        try {
            result = agent.httpPost(url, headers, params, encode, POST_TIMEOUT);
        } catch (IOException ioe) {
            LOGGER.warn("[{}] [publish-single] exception, customNamespaceId={}, namespaceName={}, namespaceDesc={}, msg={}", namespaceId, namespaceName,
                    namespaceDesc, ioe.toString());
            return false;
        }
        if (HttpURLConnection.HTTP_OK == result.code) {
            LOGGER.info("[{}] [publish-single] ok, customNamespaceId={}, namespaceName={}, namespaceDesc={}", namespaceId, namespaceName,
                    namespaceDesc);
            return true;
        } else if (HttpURLConnection.HTTP_FORBIDDEN == result.code) {
            LOGGER.info("[{}] [publish-single] ok, customNamespaceId={}, namespaceName={}, namespaceDesc={},code={}, msg={}", namespaceId, namespaceName,
                    namespaceDesc, result.code, result.content);
            throw new NacosException(result.code, result.content);
        } else {
            LOGGER.info("[{}] [publish-single] ok, customNamespaceId={}, namespaceName={}, namespaceDesc={},code={}, msg={}", namespaceId, namespaceName,
                    namespaceDesc, result.code, result.content);
            return false;
        }
    }

    private String null2defaultGroup(String group) {
        return (null == group) ? Constants.DEFAULT_GROUP : group.trim();
    }

    public static String parseNamespace(Properties properties) {
        String namespaceTmp = null;

        String isUseCloudNamespaceParsing =
                properties.getProperty(PropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING,
                        System.getProperty(SystemPropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING,
                                String.valueOf(Constants.DEFAULT_USE_CLOUD_NAMESPACE_PARSING)));

        if (Boolean.parseBoolean(isUseCloudNamespaceParsing)) {
            namespaceTmp = TemplateUtils.stringBlankAndThenExecute(namespaceTmp, new Callable<String>() {
                @Override
                public String call() {
                    return TenantUtil.getUserTenantForAcm();
                }
            });

            namespaceTmp = TemplateUtils.stringBlankAndThenExecute(namespaceTmp, new Callable<String>() {
                @Override
                public String call() {
                    String namespace = System.getenv(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_NAMESPACE);
                    return StringUtils.isNotBlank(namespace) ? namespace : StringUtils.EMPTY;
                }
            });
        }

        if (StringUtils.isBlank(namespaceTmp)) {
            namespaceTmp = properties.getProperty(PropertyKeyConst.NAMESPACE);
        }
        return StringUtils.isNotBlank(namespaceTmp) ? namespaceTmp.trim() : StringUtils.EMPTY;
    }
}
