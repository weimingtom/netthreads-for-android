package jp.gr.java_conf.abagames.bulletml;

import org.w3c.dom.*;

import org.xml.sax.*;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.*;


/**
 * <b>Param</b> is generated by Relaxer based on bulletml.rlx.
 * This class is derived from:
 *
 * <!-- for programmer
 * <elementRule role="param" type="string"/>
 *
 * <tag name="param"/>
 * -->
 * <!-- for javadoc -->
 * <pre> &lt;elementRule role="param" type="string"/&gt;
 * &lt;tag name="param"/&gt;
 * </pre>
 *
 * @version bulletml.rlx 0.21 (Sun Jun 03 09:44:34 JST 2001)
 * @author  Relaxer 0.13 (http://www.relaxer.org)
 */
public class Param implements java.io.Serializable, IRNSContainer, IRNode
{
    private RNSContext rNSContext_ = new RNSContext(this);
    private String content_;
    private IRNode parentRNode_;

    /**
     * Creates a <code>Param</code>.
     *
     */
    public Param()
    {
    }

    /**
     * Creates a <code>Param</code> by the Stack <code>stack</code>
     * that contains Elements.
     * This constructor is supposed to be used internally
     * by the Relaxer system.
     *
     * @param stack
     */
    public Param(RStack stack)
    {
        setup(stack);
    }

    /**
     * Creates a <code>Param</code> by the Document <code>doc</code>.
     *
     * @param doc
     */
    public Param(Document doc)
    {
        setup(doc.getDocumentElement());
    }

    /**
     * Creates a <code>Param</code> by the Element <code>element</code>.
     *
     * @param element
     */
    public Param(Element element)
    {
        setup(element);
    }

    /**
     * Creates a <code>Param</code> by the File <code>file</code>.
     *
     * @param file
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Param(File file) throws IOException, SAXException, ParserConfigurationException
    {
        setup(file);
    }

    /**
     * Creates a <code>Param</code>
     * by the String representation of URI <code>uri</code>.
     *
     * @param uri
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Param(String uri) throws IOException, SAXException, ParserConfigurationException
    {
        setup(uri);
    }

    /**
     * Creates a <code>Param</code> by the URL <code>url</code>.
     *
     * @param url
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Param(URL url) throws IOException, SAXException, ParserConfigurationException
    {
        setup(url);
    }

    /**
     * Creates a <code>Param</code> by the InputStream <code>in</code>.
     *
     * @param in
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Param(InputStream in) throws IOException, SAXException, ParserConfigurationException
    {
        setup(in);
    }

    /**
     * Creates a <code>Param</code> by the InputSource <code>is</code>.
     *
     * @param is
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Param(InputSource is) throws IOException, SAXException, ParserConfigurationException
    {
        setup(is);
    }

    /**
     * Creates a <code>Param</code> by the Reader <code>reader</code>.
     *
     * @param reader
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Param(Reader reader) throws IOException, SAXException, ParserConfigurationException
    {
        setup(reader);
    }

    /**
     * Initializes the <code>Param</code> by the Document <code>doc</code>.
     *
     * @param doc
     */
    public void setup(Document doc)
    {
        setup(doc.getDocumentElement());
    }

    /**
     * Initializes the <code>Param</code> by the Element <code>element</code>.
     *
     * @param element
     */
    public void setup(Element element)
    {
        init(element);
    }

    /**
     * Initializes the <code>Param</code> by the Stack <code>stack</code>
     * that contains Elements.
     * This constructor is supposed to be used internally
     * by the Relaxer system.
     *
     * @param stack
     */
    public void setup(RStack stack)
    {
        setup(stack.popElement());
    }

    /**
     * @param element
     */
    private void init(Element element)
    {
        RStack stack = new RStack(element);
        rNSContext_.declareNamespace(element);
        content_ = URelaxer.getElementPropertyAsString(element);
    }

    /**
     * Creates a DOM representation of the object.
     * Result is appended to the Node <code>parent</code>.
     *
     * @param parent
     */
    public void makeElement(Node parent)
    {
        Document doc;

        if (parent instanceof Document)
        {
            doc = (Document) parent;
        }
        else
        {
            doc = parent.getOwnerDocument();
        }

        Element element = doc.createElementNS("http://www.asahi-net.or.jp/~cs8k-cyu/bulletml", "param");
        rNSContext_.setupNamespace(element);
        URelaxer.setElementPropertyByString(element, this.content_);

        int size;
        parent.appendChild(element);
    }

    /**
     * Initializes the <code>Param</code> by the File <code>file</code>.
     *
     * @param file
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public void setup(File file) throws IOException, SAXException, ParserConfigurationException
    {
        setup(file.toURL());
    }

    /**
     * Initializes the <code>Param</code>
     * by the String representation of URI <code>uri</code>.
     *
     * @param uri
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public void setup(String uri) throws IOException, SAXException, ParserConfigurationException
    {
        setup(UJAXP.getValidDocument(uri));
    }

    /**
     * Initializes the <code>Param</code> by the URL <code>url</code>.
     *
     * @param url
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public void setup(URL url) throws IOException, SAXException, ParserConfigurationException
    {
        setup(UJAXP.getValidDocument(url));
    }

    /**
     * Initializes the <code>Param</code> by the InputStream <code>in</code>.
     *
     * @param in
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public void setup(InputStream in) throws IOException, SAXException, ParserConfigurationException
    {
        setup(UJAXP.getValidDocument(in));
    }

    /**
     * Initializes the <code>Param</code> by the InputSource <code>is</code>.
     *
     * @param is
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public void setup(InputSource is) throws IOException, SAXException, ParserConfigurationException
    {
        setup(UJAXP.getValidDocument(is));
    }

    /**
     * Initializes the <code>Param</code> by the Reader <code>reader</code>.
     *
     * @param reader
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public void setup(Reader reader) throws IOException, SAXException, ParserConfigurationException
    {
        setup(UJAXP.getValidDocument(reader));
    }

    /**
     * Creates a DOM document representation of the object.
     *
     * @exception ParserConfigurationException
     * @return Document
     */
    public Document makeDocument() throws ParserConfigurationException
    {
        Document doc = UJAXP.makeDocument();
        makeElement(doc);

        return (doc);
    }

    /**
     * Gets the RNSContext property <b>rNSContext</b>.
     *
     * @return RNSContext
     */
    public final RNSContext getRNSContext()
    {
        return (rNSContext_);
    }

    /**
     * Sets the RNSContext property <b>rNSContext</b>.
     *
     * @param rNSContext
     */
    public final void setRNSContext(RNSContext rNSContext)
    {
        this.rNSContext_ = rNSContext;
    }

    /**
     * Gets the String property <b>content</b>.
     *
     * @return String
     */
    public final String getContent()
    {
        return (content_);
    }

    /**
     * Sets the String property <b>content</b>.
     *
     * @param content
     */
    public final void setContent(String content)
    {
        this.content_ = content;
    }

    /**
     * Makes a XML text representation.
     *
     * @return String
     */
    public String makeTextDocument()
    {
        StringBuffer buffer = new StringBuffer();
        makeTextElement(buffer);

        return (new String(buffer));
    }

    /**
     * Makes a XML text representation.
     *
     * @param buffer
     */
    public void makeTextElement(StringBuffer buffer)
    {
        int size;
        String prefix = rNSContext_.getPrefixByUri("http://www.asahi-net.or.jp/~cs8k-cyu/bulletml");
        buffer.append("<");
        URelaxer.makeQName(prefix, "param", buffer);
        rNSContext_.makeNSMappings(buffer);
        buffer.append(">");
        buffer.append("</");
        URelaxer.makeQName(prefix, "param", buffer);
        buffer.append(">");
    }

    /**
     * Makes a XML text representation.
     *
     * @param buffer
     */
    public void makeTextElement(PrintWriter buffer)
    {
        int size;
        String prefix = rNSContext_.getPrefixByUri("http://www.asahi-net.or.jp/~cs8k-cyu/bulletml");
        buffer.print("<");
        URelaxer.makeQName(prefix, "param", buffer);
        rNSContext_.makeNSMappings(buffer);
        buffer.print(">");
        buffer.print("</");
        URelaxer.makeQName(prefix, "param", buffer);
        buffer.print(">");
    }

    /**
     * Gets the IRNode property <b>parentRNode</b>.
     *
     * @return IRNode
     */
    public final IRNode getParentRNode()
    {
        return (parentRNode_);
    }

    /**
     * Sets the IRNode property <b>parentRNode</b>.
     *
     * @param parentRNode
     */
    public final void setParentRNode(IRNode parentRNode)
    {
        this.parentRNode_ = parentRNode;
    }

    /**
     * Gets child RNodes.
     *
     * @return IRNode[]
     */
    public IRNode[] getRNodes()
    {
        java.util.List classNodes = new java.util.ArrayList();
        IRNode[] nodes = new IRNode[classNodes.size()];

        return ((IRNode[]) classNodes.toArray(nodes));
    }

    /**
     * Tests if a Element <code>element</code> is valid
     * for the <code>Param</code>.
     *
     * @param element
     * @return boolean
     */
    public static boolean isMatch(Element element)
    {
        if (!URelaxer2.isTargetElement(element, "http://www.asahi-net.or.jp/~cs8k-cyu/bulletml", "param"))
        {
            return (false);
        }

        RStack target = new RStack(element);
        Element child;

        if (!target.isEmptyElement())
        {
            return (false);
        }

        return (true);
    }

    /**
     * Tests if elements contained in a Stack <code>stack</code>
     * is valid for the <code>Param</code>.
     * This mehtod is supposed to be used internally
     * by the Relaxer system.
     *
     * @param stack
     * @return boolean
     */
    public static boolean isMatch(RStack stack)
    {
        Element element = stack.peekElement();

        if (element == null)
        {
            return (false);
        }

        return (isMatch(element));
    }

    /**
     * Tests if elements contained in a Stack <code>stack</code>
     * is valid for the <code>Param</code>.
     * This method consumes the stack contents during matching operation.
     * This mehtod is supposed to be used internally
     * by the Relaxer system.
     *
     * @param stack
     * @return boolean
     */
    public static boolean isMatchHungry(RStack stack)
    {
        Element element = stack.peekElement();

        if (element == null)
        {
            return (false);
        }

        if (isMatch(element))
        {
            stack.popElement();

            return (true);
        }
        else
        {
            return (false);
        }
    }
}
