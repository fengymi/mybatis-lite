package com.github.mustfun.mybatis.plugin.util;

import com.intellij.openapi.util.IconLoader;
import com.intellij.spring.SpringApiIcons;
import com.intellij.util.PlatformIcons;

import javax.swing.*;

/**
 * @author yanglin
 */
public interface Icons {

    Icon MYBATIS_LOGO = IconLoader.getIcon("/javaee/persistenceId.png");


    Icon PARAM_COMPLETION_ICON = PlatformIcons.PARAMETER_ICON;
    Icon FIELD_COMPLETION_ICON = PlatformIcons.FIELD_ICON;

    Icon MAPPER_LINE_MARKER_ICON = IconLoader.getIcon("/images/down.png");

    Icon STATEMENT_LINE_MARKER_ICON = IconLoader.getIcon("/images/up.png");

    Icon SPRING_BEAN_ICON = IconLoader.getIcon("/images/springBean.png");
    Icon SPRING_INJECTION_ICON = IconLoader.getIcon("/images/showAutowiredDependencies.png");
    Icon SPRING_INJECTION_ICON_NEW = SpringApiIcons.Gutter.ShowAutowiredDependencies;
    Icon MYBATIS_LOGO_TRUE = IconLoader.getIcon("/images/logo_raw.png");

    Icon MYBATIS_FAVICON = IconLoader.getIcon("/images/favicon.ico");
    Icon MYBATIS_LOGO_MINI = IconLoader.getIcon("/images/logo_17.png");
}