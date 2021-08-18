package com.excelsecu.cmsystem.mapper;

import com.excelsecu.cmsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.excelsecu.cmsystem.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询
     * @param type [0, 1]
     * @param name 0:name是username; 1:name是orgname
     * @param offset   起始索引,从0开始
     * @param pageSize 页大小
     * @return
     */
    List<UserRole> quereyList(@Param("type") Integer type, @Param("name") String name, @Param("offset")Long offset, @Param("pageSize")Long pageSize);

    /**
     * 查询总量, 与queryList配合
     * @param type
     * @param name
     * @return
     */
    Long queryListSize(@Param("type") Integer type, @Param("name") String name);

}
