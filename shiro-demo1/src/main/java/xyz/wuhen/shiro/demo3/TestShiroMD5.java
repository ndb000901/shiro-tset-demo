package xyz.wuhen.shiro.demo3;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestShiroMD5 {
    public static void main(String[] args) {

        // md5加密
        Md5Hash md5Hash = new Md5Hash("123456");
        System.out.println(md5Hash.toHex());
        //md5 + salt
        Md5Hash md5Hash1 = new Md5Hash("123456","sjshhsx");
        System.out.println(md5Hash1.toHex());

        //md5 + salt + hash散列
        Md5Hash md5Hash2 = new Md5Hash("123456","sjshhsx",1024);
        System.out.println(md5Hash2.toHex());
    }
}
