package com.cesar;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

public class ConexaoJBoss extends ManipuarArquivo {

    public static Object lookupRemoteStateless(Class classBean, Class classInterface, String i_appName, String i_moduleName, String i_ipJboss, String i_PortaJBoss, String i_UsuarioJboss, String i_SenhaJboss) throws NamingException {
        Properties props = new Properties();

        props.put("endpoint.name", "endpoint");

        props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");

        props.put("remote.connections", "default");

        String IPJBoss = i_ipJboss;
        String PortaJBoss = i_PortaJBoss;
        String UsuarioJboss = i_UsuarioJboss;
        String SenhaJboss = i_SenhaJboss;

        if (IPJBoss == null || IPJBoss.trim().length() == 0) {
            try {
                IPJBoss = lePropriedade("", "TesteEJB.properties", "IPJBoss");
                PortaJBoss = lePropriedade("", "TesteEJB.properties", "PortaJBoss");
                UsuarioJboss = lePropriedade("", "TesteEJB.properties", "UsuarioJboss");
                SenhaJboss = lePropriedade("", "TesteEJB.properties", "SenhaJboss");
            } catch (Exception ex) {
                IPJBoss = "localhost";
                PortaJBoss = "4447";
                UsuarioJboss = "appuser";
                SenhaJboss = "C0v48r4kT#";
            }
        }
        System.out.println("=========================");
        System.out.println("IPJBoss: " + IPJBoss);
        System.out.println("PortaJBoss: " + PortaJBoss);
        System.out.println("UsuarioJboss: " + UsuarioJboss);
        System.out.println("SenhaJboss: " + SenhaJboss);
        System.out.println("=========================");

        props.put("remote.connection.default.host", IPJBoss);

        props.put("remote.connection.default.port", PortaJBoss);

        props.put("remote.connection.default.username", UsuarioJboss);

        props.put("remote.connection.default.password", SenhaJboss);

        props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        EJBClientConfiguration ejbClientConfiguration = new PropertiesBasedEJBClientConfiguration(props);

        final ContextSelector<EJBClientContext> ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(ejbClientConfiguration);

        final ContextSelector<EJBClientContext> previousSelector = EJBClientContext.setSelector(ejbClientContextSelector);

        Properties propsEnv = new Properties();

        propsEnv.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        propsEnv.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
        propsEnv.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
        propsEnv.put("java.naming.provider.url", "remote://" + IPJBoss + ":" + PortaJBoss);
        propsEnv.put("java.naming.security.principal", UsuarioJboss);
        propsEnv.put("java.naming.security.credentials", SenhaJboss);

        final Context context = new javax.naming.InitialContext(propsEnv);

        final String appName = i_appName;

        final String moduleName = i_moduleName;

        final String distinctName = "";

        final String beanName = classBean.getSimpleName();

        final String viewClassName = classInterface.getName();

        System.out.println("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);


        if (classBean.getAnnotation(javax.ejb.Stateful.class) != null) {
            return context.lookup("ejb:" + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
        } else {
            return context.lookup("ejb:" + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName);

        }
    }
    private static String IPJBoss = "";
    private static String PortaJBoss = "";
    private static String UsuarioJboss = "";
    private static String SenhaJboss = "";
    private static ContextSelector<EJBClientContext> ejbClientContextSelector;
    private static ConexaoJBoss instance;

    public static ConexaoJBoss getInstance() {
        if (instance == null) {
            instance = new ConexaoJBoss();
        }
        return instance;
    }

    private ConexaoJBoss() {
        Properties props = new Properties();

        props.put("endpoint.name", "endpoint");

        props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");

        props.put("remote.connections", "default");

        try {
            IPJBoss = lePropriedade("", "TesteEJB.properties", "IPJBoss");
            PortaJBoss = lePropriedade("", "TesteEJB.properties", "PortaJBoss");
            UsuarioJboss = lePropriedade("", "TesteEJB.properties", "UsuarioJboss");
            SenhaJboss = lePropriedade("", "TesteEJB.properties", "SenhaJboss");
        } catch (Exception ex) {
            IPJBoss = "localhost";
            PortaJBoss = "4447";
            UsuarioJboss = "appuser";
            SenhaJboss = "C0v48r4kT#";
        }
        props.put("remote.connection.default.host", IPJBoss);

        props.put("remote.connection.default.port", PortaJBoss);

        props.put("remote.connection.default.username", UsuarioJboss);

        props.put("remote.connection.default.password", SenhaJboss);

        props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        EJBClientConfiguration ejbClientConfiguration = new PropertiesBasedEJBClientConfiguration(props);

        ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(ejbClientConfiguration);


    }

    //============================================================================================================================================
    public Object lookupRemote(Class classBean, Class classInterface) throws NamingException {

        final ContextSelector<EJBClientContext> previousSelector = EJBClientContext.setSelector(ejbClientContextSelector);

        Properties propsEnv = new Properties();

        propsEnv.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        propsEnv.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
        propsEnv.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
        propsEnv.put("java.naming.provider.url", "remote://" + IPJBoss + ":" + PortaJBoss);
        propsEnv.put("java.naming.security.principal", UsuarioJboss);
        propsEnv.put("java.naming.security.credentials", SenhaJboss);

        final Context context = new javax.naming.InitialContext(propsEnv);

        final String appName = "TesteEJB-ear-1.0-SNAPSHOT";

        final String moduleName = "TesteEJB-ejb-1.0-SNAPSHOT";

        final String distinctName = "";

        final String beanName = classBean.getSimpleName();

        final String viewClassName = classInterface.getName();

        Object objetoRetorno;

        if (classBean.getAnnotation(javax.ejb.Stateful.class) != null) {
            objetoRetorno = context.lookup("ejb:" + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
        } else {
            objetoRetorno = context.lookup("ejb:" + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName);
        }

        context.close();

        return objetoRetorno;


    }

    public static Object lookupLocalEjb(Class classBean, Class classInterface) throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);

        final String appName = "TesteEJB-ear-1.0-SNAPSHOT";

        final String moduleName = "TesteEJB-ejb-1.0-SNAPSHOT";

        final String distinctName = "";

        final String beanName = classBean.getSimpleName();

        final String viewClassName = classInterface.getName();

        //return context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
        if (classBean.getAnnotation(javax.ejb.Stateful.class) != null) {
            return context.lookup("ejb:" + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
        } else {
            return context.lookup("ejb:" + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName);

        }
    }
}
