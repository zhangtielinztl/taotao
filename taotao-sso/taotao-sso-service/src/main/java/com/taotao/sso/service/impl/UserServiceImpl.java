package com.taotao.sso.service.impl;


import com.taotao.commom.utils.JsonUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private TbUserMapper tbUserMapper;
  private  TaotaoResult result;
  @Autowired
  private JedisClient jedisClient;
  @Value("${USER_INFO}")
  private  String USER_INFO;
  @Value("${SESSION_EXPIRE}")
          private  int SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String param, int type) {
        if(type==1){
            TbUser tbUser = tbUserMapper.getUserByUserName(param);
            if(tbUser !=null){
                return  TaotaoResult.ok(false);
            }
        }else if(type==2){
            TbUser tbUser = tbUserMapper.getUserByPhone(param);
            if(tbUser !=null){
                return TaotaoResult.ok(false);
            }
        }else if(type==3){
            TbUser tbUser = tbUserMapper.getUserByEmail(param);
            if(tbUser !=null){
                return  TaotaoResult.ok(false);
            }
        }else{
            return  TaotaoResult.build(500,"ok","传入的参数类型不合法");
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult createUser(TbUser tbUser) {
        //校验账号是否为空
        if(StringUtils.isBlank(tbUser.getUserName())){
            return  TaotaoResult.build(400,"账号不能为空");
        }
            //验证密码是否为空
            if(StringUtils.isBlank(tbUser.getPassWord())){
            return  TaotaoResult.build(400,"密码不能为空");
            }
        //校验手机号是否为空
        if(StringUtils.isBlank(tbUser.getPhone())){
            return  TaotaoResult.build(400,"手机号吗不能为空");
        }
        //校验邮箱是否为空
        if(StringUtils.isBlank(tbUser.getEmail())){
            return  TaotaoResult.build(400,"邮箱不能为空");
        }
            //检验账号是否重复
         result = checkData(tbUser.getUserName(),1);
            if (!(boolean) result.getData()){
                return  TaotaoResult.build(400,"此用户名已经被使用");
            }
        //检验手机号是否重复
        result = checkData(tbUser.getPhone(),2);
        if (!(boolean) result.getData()){
            return  TaotaoResult.build(400,"此手机号已经被使用");
        }
        //检验邮箱是否重复
        result =checkData(tbUser.getEmail(),3);
        if (!(boolean) result.getData()){
            return  TaotaoResult.build(400,"此邮箱已经被使用");
        }
        //2 补全Tbuser其他属性
        Date date =new Date();
        tbUser.setCreated(date);
         tbUser.setUpdated(date);
         //3 密码要md5加密
        String md5PassWord = DigestUtils.md5DigestAsHex(tbUser.getPassWord().getBytes());
        tbUser.setPassWord(md5PassWord);

        tbUserMapper.insertUser(tbUser);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult loginUser(String userName, String passWord) {
        //校验账号是否为空
        if(StringUtils.isBlank(userName)){
            return  TaotaoResult.build(400,"账号不能为空");
        }
        //验证密码是否为空
        if(StringUtils.isBlank(passWord)){
            return  TaotaoResult.build(400,"密码不能为空");
        }
        //注意密码要进行md5加密验证
        TbUser tbUser = tbUserMapper.getUserByUserAndPass(userName, DigestUtils.md5DigestAsHex(passWord.getBytes()));
        if(tbUser==null){
            return  TaotaoResult.build(400,"账号密码有误，请重新输入");
        }
        //uuid生成的随机id为 fawel-aa-a这种形式，写下面这段代码后就不会有-这个符号了
        String uuid = UUID.randomUUID().toString();
        //生成token
        String token =uuid.replace("-","");
//注意密码不能存入进去
       tbUser.setPassWord(null);
        //存入到redis中
        jedisClient.set(USER_INFO+":"+token,JsonUtils.objectToJson(tbUser));
        //设置过期时间   Cannot find local variable 'itemId'
        jedisClient.expire(USER_INFO+":"+token,SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }

    @Override

    public TaotaoResult getUserByToken(String token) {

        String json = jedisClient.get(USER_INFO + ":" + token);

        if (StringUtils.isBlank(json)) {

            // 3、如果查询不到数据。返回用户已经过期。

            return TaotaoResult.build(400, "用户登录已经过期，请重新登录。");

        }

        //如果直接这样返回 那么 他认为你这个东西 是String字符串 变成的json 他会自动加上转移符

        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);

        return TaotaoResult.ok(tbUser);
    }
}
