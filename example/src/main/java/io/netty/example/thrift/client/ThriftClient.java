package io.netty.example.thrift.client;

import io.netty.example.thrift.interfaces.Person;
import io.netty.example.thrift.interfaces.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {
    public static void main(String[] args) {
        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 9999), 600);
        TProtocol protocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();
            Person p = client.getPersonByUsername("张三");
            System.out.println(p.getUsername() + ", " + p.getAge() + ", " + p.isMarried());
            System.out.println("+++++++++++++++++");
            Person per = new Person();
            per.setUsername("哈哈");
            per.setAge(22);
            per.setMarried(false);
            client.savePerson(per);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            tTransport.close();
        }

    }
}
