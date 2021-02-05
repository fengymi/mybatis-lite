package com.github.mustfun.mybatis.plugin.provider;

import com.github.mustfun.mybatis.plugin.dom.model.IdDomElement;
import com.github.mustfun.mybatis.plugin.service.JavaService;
import com.github.mustfun.mybatis.plugin.setting.MybatisLiteSetting;
import com.github.mustfun.mybatis.plugin.util.Icons;
import com.github.mustfun.mybatis.plugin.util.JavaUtils;
import com.github.mustfun.mybatis.plugin.util.MybatisConstants;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiParameterListOwner;
import com.intellij.psi.impl.source.PsiParameterImpl;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.CommonProcessors;
import com.intellij.util.xml.DomElement;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * @author yanglin
 * @update itar
 * @function 继承RelatedItemLineMarkerProvider 实现标记和跳转，修改性能
 * 方法级别跳转  -  方法跳转到方法   PsiNameIdentifierOwner 有标识符名称的一个所有者，所有有标识符的地方都会继承他,包括方法里面有标识符的一行代码，如List<String> aa =new ArrayList<>()
 */
public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    private static final Function<DomElement, XmlTag> FUN = new Function<DomElement, XmlTag>() {
        @Override
        public XmlTag apply(DomElement domElement) {
            return domElement.getXmlTag();
        }
    };

  @Override
  protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
    Map<String, String> valueMap = MybatisLiteSetting.getInstance().getValueMap();
    if (!MybatisConstants.TRUE.equalsIgnoreCase(valueMap.get(MybatisConstants.NAVIGATION_OPEN_STATUS))) {
      return;
    }

    //如果element是PsiNameIdentifierOwner对象(String id (参数))   int deleteByPrimary(String id) (方法))，且是接口
    if (element instanceof PsiNameIdentifierOwner && !(element instanceof PsiParameterImpl)
            && JavaUtils.isElementWithinInterface(element)) {
      //表示ID元素的一个集合列表
      CommonProcessors.CollectProcessor<IdDomElement> processor = new CommonProcessors.CollectProcessor<>();
      //找出xml的namespace跟element相等的文件
      JavaService.getInstance(element.getProject()).process(element, processor);
      Collection<IdDomElement> results = processor.getResults();
      if (!results.isEmpty()) {

        //构建导航图标的builder
        NavigationGutterIconBuilder<PsiElement> builder =
                NavigationGutterIconBuilder.create(Icons.MAPPER_LINE_MARKER_ICON)
                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                        //target是xmlTag
                        .setTargets(Collections2.transform(results, FUN))
                        .setTooltipTitle("导航到mapper xml中的方法");
        RelatedItemLineMarkerInfo<PsiElement> lineMarkerInfo = builder.createLineMarkerInfo(
                Objects.requireNonNull(((PsiNameIdentifierOwner) element).getNameIdentifier()));
        result.add(lineMarkerInfo);
      }
    }
  }

}
