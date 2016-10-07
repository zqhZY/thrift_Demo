package com.someth.www;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import Rpc.*;

/**
 * Created by asus-pc on 2016/10/7.
 */
public class App {

    private static Configuration config;
    private static final Log LOG = LogFactory.getLog(App.class);

    public static CalculatorHandler handler;
    public static Calculator.Processor processor;


    private void initConf() throws ConfigurationException {
        config = new PropertiesConfiguration(this.getClass().getClassLoader().getResource("rpc-server.properties"));
    }

    public  void start(){
        try{
            initConf();
            handler = new CalculatorHandler();
            processor = new Calculator.Processor(handler);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };
            new Thread(simple).start();

        }catch (Exception x){
            LOG.error("something error in start(), the detail is " + x);
        }
    }

    public static void simple(Calculator.Processor processor) {
        try {
            Integer port = config.getInt("server.port");
            TServerTransport serverTransport = new TServerSocket(port);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            LOG.info("server started....................... ");
            server.serve();
        } catch (Exception x) {
            LOG.error("something error in simple(), the detail is " + x);
        }
    }

    public static void main(String [] args) {
        App app = new App();
        app.start();
    }

}

