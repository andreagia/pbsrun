package org.cirmmp.pbsrun.service;

public class DefaultHelloService implements HelloService {

    @Override
    public void hello() {
        System.out.println("CIAO CIAO");
    }
}
