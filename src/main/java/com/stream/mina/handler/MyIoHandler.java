/**
 * 汇付天下有限公司
 * Copyright (c) 2006-2015 ChinaPnR,Inc.All Rights Reserved.
 */
package com.stream.mina.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xi.wei
 * @version 1.0: MyIoHandler.java, v 0.1 2017/4/28 21:52  xi.wei Exp $
 */
public class MyIoHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MyIoHandler.class);

    @Override
    public void sessionOpened(IoSession session) throws Exception {

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {

    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String ip = session.getRemoteAddress().toString();
        logger.info("===> Message From " + ip + " : " + message);
    }
}
