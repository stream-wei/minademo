package com.stream.mina.server;

import com.stream.mina.handler.MyIoHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 *MINA心跳
 * @author xi.wei
 * @version 1.0: Server.java, v 0.1 2017/4/28 21:26  xi.wei Exp $
 */
public class Server {

    private static final int PORT = 9123;
    /** 30秒后超时 */
    private static final int IDELTIMEOUT = 30;
    /** 15秒发送一次心跳包 */
    private static final int HEARTBEATRATE = 15;
    /** 心跳包内容 */
    private static final String HEARTBEATREQUEST = "0x11";
    private static final String HEARTBEATRESPONSE = "0x12";
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception{
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(1024);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,IDELTIMEOUT);
        acceptor.getFilterChain().addLast("logger",new LoggingFilter());
        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory()));
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactory() {
            public boolean isRequest(IoSession ioSession, Object message) {
                logger.info("请求心跳包信息: " + message);
                if (message.equals(HEARTBEATREQUEST))
                    return true;
                return false;
            }

            public boolean isResponse(IoSession ioSession, Object o) {
                return false;
            }

            public Object getRequest(IoSession ioSession) {
                logger.info("请求预设信息: " + HEARTBEATREQUEST);
                return HEARTBEATREQUEST;
            }

            public Object getResponse(IoSession ioSession, Object o) {
                logger.info("响应预设信息: " + HEARTBEATRESPONSE);
                /** 返回预设语句 */
                return HEARTBEATRESPONSE;
            }
        };
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory, IdleStatus.BOTH_IDLE);
        //设置是否forward到下一个filter
        heartBeat.setForwardEvent(true);
        //设置心跳频率
        heartBeat.setRequestInterval(HEARTBEATRATE);

        acceptor.getFilterChain().addLast("heartbeat", heartBeat);

        acceptor.setHandler(new MyIoHandler());
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("Server started on port： " + PORT);
    }
}
