package com.codermast.imagebedbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codermast.imagebedbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
