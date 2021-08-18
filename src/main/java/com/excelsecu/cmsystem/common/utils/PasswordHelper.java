package com.excelsecu.cmsystem.common.utils;

import com.excelsecu.cmsystem.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户密码加密
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        ByteSource bytes = ByteSource.Util.bytes(user.getSalt());
        SimpleHash simpleHash = new SimpleHash(
                algorithmName,
                user.getPassword(),
                bytes,
                hashIterations);
        String newPassword = simpleHash.toHex();
        user.setPassword(newPassword);
    }

    public boolean verifyPassword(User user, String password) {
        String hash = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations
        ).toHex();
        return user.getPassword().equals(hash);
    }

}
