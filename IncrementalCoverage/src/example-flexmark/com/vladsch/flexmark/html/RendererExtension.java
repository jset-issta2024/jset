package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.util.options.MutableDataHolder;

public interface RendererExtension extends com.vladsch.flexmark.util.builder.Extension {
    /**
     * This method is called first on all extensions so that they can adjust the options that must be
     * common to all extensions.
     *
     * @param options option set that will be used for the builder
     */
    void rendererOptions(MutableDataHolder options);

    /**
     * Called to give each extension to register extension points that it contains
     *
     * @param rendererBuilder builder to call back for extension point registration
     * @param rendererType    type of rendering being performed. For now "HTML", "JIRA" or "YOUTRACK"
     * @see HtmlRenderer.Builder#attributeProviderFactory(AttributeProviderFactory)
     * @see HtmlRenderer.Builder#linkResolverFactory(LinkResolverFactory)
     * @see HtmlRenderer.Builder#htmlIdGeneratorFactory(HeaderIdGeneratorFactory)
     */
    void extend(RendererBuilder rendererBuilder, String rendererType);
}
