package com.socket.netty.sendobject.coder;

import com.socket.netty.sendobject.bean.Person;
import com.socket.netty.sendobject.utils.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
 /**
  * 序列化
  * 将object转换成Byte[]
  * @author wilson
  *
  */

 //MessageToByteEncoder已经继承out
public class PersonEncoder extends MessageToByteEncoder<Person> {
 
    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
    	//工具类：将object转换为byte[]
        byte[] datas = ByteObjConverter.objectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}
