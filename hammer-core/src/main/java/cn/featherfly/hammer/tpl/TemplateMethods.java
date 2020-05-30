
package cn.featherfly.hammer.tpl;

import java.util.HashMap;
import java.util.Map;

import cn.featherfly.common.lang.Lang;
import cn.featherfly.hammer.HammerException;

/**
 * <p>
 * TemplateMethodes
 * </p>
 * 
 * @author zhongj
 */
public class TemplateMethods<M> {

    public static final String DEFAULT_PREFIX = "tpl";

    // public static final String PROPERTIES_METHOD_KEY = "prop";
    public static final String WRAP_METHOD_KEY = "wrap";

    private static final String[] REQUIRED_KEYS = { WRAP_METHOD_KEY };

    protected Map<String, M> methodMap = new HashMap<>();

    private String prefix = DEFAULT_PREFIX;

    /**
     * get prefix
     * 
     * @return prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * set prefix
     * 
     * @param prefix
     *            prefix
     */
    public void setPrefix(String prefix) {
        if (Lang.isNotEmpty(prefix)) {
            this.prefix = prefix;
        }
    }

    public void addMethode(String key, M method) {
        methodMap.put(getKey(key), method);
    }

    public Map<String, M> getMethodeMap() {
        return methodMap;
    }

    public Map<String, M> getMethodeMapAfterCheck() {
        for (String key : REQUIRED_KEYS) {
            if (!methodMap.containsKey(getKey(key))) {
                throw new HammerException("method with key " + key + " is null");
            }
        }
        return methodMap;
    }

    public void addWrapMethode(M method) {
        addMethode(WRAP_METHOD_KEY, method);
    }

    private String getKey(String key) {
        return prefix + "_" + key;
    }
}
