/**
 * 汇付天下有限公司
 * Copyright (c) 2006-2015 ChinaPnR,Inc.All Rights Reserved.
 */
package com.stream.mina.demo;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xi.wei
 * @version : NIODemo.java, v 0.1 2017/4/29 15:31  xi.wei Exp $
 */
public class NIODemo {

    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("D:\\demo.txt","rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = fileChannel.read(buf);
        while (bytesRead != -1){
            //System.out.println(bytesRead);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = fileChannel.read(buf);
        }
        file.close();
    }
}
