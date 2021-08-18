package com.excelsecu.cmsystem.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class JwtSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 设置可以生成session
        context.setSessionCreationEnabled(true);
        return super.createSubject(context);
    }

}
