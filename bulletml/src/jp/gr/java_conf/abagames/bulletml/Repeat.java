package jp.gr.java_conf.abagames.bulletml;

import org.w3c.dom.*;

import org.xml.sax.*;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.*;


/**
 * <b>Repeat</b> is generated by Relaxer based on bulletml.rlx.
 * This class is derived from:
 *
 * <!-- for programmer
 * <elementRule role="repeat">
 *   <sequence>
 *     <ref label="times"/>
 *     <hedgeRef label="actionElm"/>
 *   </sequence>
 * </elementRule>
 *
 * <tag name="repeat"/>
 * -->
 * <!-- for javadoc -->
 * <pre> &lt;elementRule role="repeat"&gt;
 *   &lt;sequence&gt;
 *     &lt;ref label="times"/&gt;
 *     &lt;hedgeRef label="actionElm"/&gt;
 *   &lt;/sequence&gt;
 * &lt;/elementRule&gt;
 * &lt;tag name="repeat"/&gt;
 * </pre>
 *
 * @version bulletml.rlx 0.21 (Sun Jun 03 09:44:34 JST 2001)
 * @author  Relaxer 0.13 (http://www.relaxer.org)
 */
public class Repeat implements java.io.Serializable, IRNSContainer, IRNode, IActionChoice
{
    private RNSContext rNSContext_ = new RNSContext(this);
    private String times_;
    private IActionElmChoice actionElm_;
    private IRNode parentRNode_;

    /**
     * Creates a <code>Repeat</code>.
     *
     */
    public Repeat()
    {
    }

    /**
     * Creates a <code>Repeat</code> by the Stack <code>stack</code>
     * that contains Elements.
     * This constructor is supposed to be used internally
     * by the Relaxer system.
     *
     * @param stack
     */
    public Repeat(RStack stack)
    {
        setup(stack);
    }

    /**
     * Creates a <code>Repeat</code> by the Document <code>doc</code>.
     *
     * @param doc
     */
    public Repeat(Document doc)
    {
        setup(doc.getDocumentElement());
    }

    /**
     * Creates a <code>Repeat</code> by the Element <code>element</code>.
     *
     * @param element
     */
    public Repeat(Element element)
    {
        setup(element);
    }

    /**
     * Creates a <code>Repeat</code> by the File <code>file</code>.
     *
     * @param file
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Repeat(File file) throws IOException, SAXException, ParserConfigurationException
    {
        setup(file);
    }

    /**
     * Creates a <code>Repeat</code>
     * by the String representation of URI <code>uri</code>.
     *
     * @param uri
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Repeat(String uri) throws IOException, SAXException, ParserConfigurationException
    {
        setup(uri);
    }

    /**
     * Creates a <code>Repeat</code> by the URL <code>url</code>.
     *
     * @param url
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Repeat(URL url) throws IOException, SAXException, ParserConfigurationException
    {
        setup(url);
    }

    /**
     * Creates a <code>Repeat</code> by the InputStream <code>in</code>.
     *
     * @param in
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Repeat(InputStream in) throws IOException, SAXException, ParserConfigurationException
    {
        setup(in);
    }

    /**
     * Creates a <code>Repeat</code> by the InputSource <code>is</code>.
     *
     * @param is
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Repeat(InputSource is) throws IOException, SAXException, ParserConfigurationException
    {
        setup(is);
    }

    /**
     * Creates a <code>Repeat</code> by the Reader <code>reader</code>.
     *
     * @param reader
     * @exception IOException
     * @exception SAXException
     * @exception ParserConfigurationException
     */
    public Repeat(Reader reader) throws IOException, SAXException, ParserConfigurationException
    {
        setup(reader);
    }

    /**
     * Initializes the <code>Repeat</code> by the Document <code>doc</code>.
     *
     * @param doc
     */
    public void setup(Document doc)
    {
        setup(doc.getDocumentElement());
    }

    /**
     * Initializes the <code>Repeat</code> by the Element <code>element</code>.
     *
     * @param element
     */
    public void setup(Element element)
    {
        init(element);
    }

    /**
     * Initializes the <code>Repeat</code> by the Stack <code>stack</code>
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
        times_ = URelaxer.getElementPropertyAsString(stack.popElement());

        if (Action.isMatch(stack))
        {
            setActionElm(new Action(stack));
        }
        else if (ActionRef.isMatch(stack))
        {
            setActionElm(new ActionRef(stack));
        }
        else
        {
            throw (new IllegalArgumentException());
        }
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

        Element element = doc.createElementNS("http://www.asahi-net.or.jp/~cs8k-cyu/bulletml", "repeat");
        rNSContext_.setupNamespace(element);

        int size;
        URelaxer2.setElementPropertyByString(element, "http://www.asahi-net.or.jp/~cs8k-cyu/bulletml", "times", this.times_, rNSContext_);
        this.actionElm_.makeElement(element);
        parent.appendChild(element);
    }

    /**
     * Initializes the <code>Repeat</code> by the File <code>file</code>.
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
     * Initializes the <code>Repeat</code>
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
     * Initializes the <code>Repeat</code> by the URL <code>url</code>.
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
     * Initializes the <code>Repeat</code> by the InputStream <code>in</code>.
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
     * Initializes the <code>Repeat</code> by the InputSource <code>is</code>.
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
     * Initializes the <code>Repeat</code> by the Reader <code>reader</code>.
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
     * Gets the String property <b>times</b>.
     *
     * @return String
     */
    public final String getTimes()
    {
        return (times_);
    }

    /**
     * Sets the String property <b>times</b>.
     *
     * @param times
     */
    public final void setTimes(String times)
    {
        this.times_ = times;
    }

    /**
     * Gets the IActionElmChoice property <b>actionElm</b>.
     *
     * @return IActionElmChoice
     */
    public final IActionElmChoice getActionElm()
    {
        return (actionElm_);
    }

    /**
     * Sets the IActionElmChoice property <b>actionElm</b>.
     *
     * @param actionElm
     */
    public final void setActionElm(IActionElmChoice actionElm)
    {
        this.actionElm_ = actionElm;

        if (actionElm != null)
        {
            actionElm.setParentRNode(this);
        }
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
        URelaxer.makeQName(prefix, "repeat", buffer);
        rNSContext_.makeNSMappings(buffer);
        buffer.append(">");
        buffer.append("<");
        URelaxer.makeQName(prefix, "times", buffer);
        buffer.append(">");
        buffer.append(URelaxer.escapeCharData(getTimes()));
        buffer.append("</");
        URelaxer.makeQName(prefix, "times", buffer);
        buffer.append(">");
        actionElm_.makeTextElement(buffer);
        buffer.append("</");
        URelaxer.makeQName(prefix, "repeat", buffer);
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
        URelaxer.makeQName(prefix, "repeat", buffer);
        rNSContext_.makeNSMappings(buffer);
        buffer.print(">");
        buffer.print("<");
        URelaxer.makeQName(prefix, "times", buffer);
        buffer.print(">");
        buffer.print(URelaxer.escapeCharData(getTimes()));
        buffer.print("</");
        URelaxer.makeQName(prefix, "times", buffer);
        buffer.print(">");
        actionElm_.makeTextElement(buffer);
        buffer.print("</");
        URelaxer.makeQName(prefix, "repeat", buffer);
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
        classNodes.add(actionElm_);

        IRNode[] nodes = new IRNode[classNodes.size()];

        return ((IRNode[]) classNodes.toArray(nodes));
    }

    /**
     * Tests if a Element <code>element</code> is valid
     * for the <code>Repeat</code>.
     *
     * @param element
     * @return boolean
     */
    public static boolean isMatch(Element element)
    {
        if (!URelaxer2.isTargetElement(element, "http://www.asahi-net.or.jp/~cs8k-cyu/bulletml", "repeat"))
        {
            return (false);
        }

        RStack target = new RStack(element);
        Element child;
        child = target.popElement();

        if (child == null)
        {
            return (false);
        }

        if (!URelaxer2.isTargetElement(child, "http://www.asahi-net.or.jp/~cs8k-cyu/bulletml", "times"))
        {
            return (false);
        }

        if (Action.isMatchHungry(target))
        {
        }
        else if (ActionRef.isMatchHungry(target))
        {
        }
        else
        {
            return (false);
        }

        if (!target.isEmptyElement())
        {
            return (false);
        }

        return (true);
    }

    /**
     * Tests if elements contained in a Stack <code>stack</code>
     * is valid for the <code>Repeat</code>.
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
     * is valid for the <code>Repeat</code>.
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