package com.chinaunicom.software.origin.common.jenkins.script.node;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.chinaunicom.software.origin.common.jenkins.script.refer.ScriptNode;
import com.chinaunicom.software.origin.common.jenkins.script.refer.NodeType;
import com.chinaunicom.software.origin.common.jenkins.script.refer.ScriptMark;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public abstract class ParentNode {

    private String name;

    @ScriptMark(type = NodeType.Ignore)
    @JsonIgnore
    private int level;

    @ScriptMark(type = NodeType.Ignore)
    @JsonIgnore
    @Getter
    private String selfType = this.getClass().getSimpleName().toLowerCase();

    @ScriptMark(type = NodeType.Simple)
    private String agent;

    @ScriptMark(type = NodeType.Simple)
    private Boolean failFast;

    @ScriptMark(type = NodeType.Block)
    private String block;

    @ScriptMark(type = NodeType.Complex)
    private Node node;

    @ScriptMark(type = NodeType.Complex)
    private Parameters parameters;

    @ScriptMark(type = NodeType.Complex)
    private Envirment envirment;

    @ScriptMark(type = NodeType.Param)
    private String_ string;

    @ScriptMark(type = NodeType.Complex)
    private Stages stages;

    @ScriptMark(type = NodeType.Complex)
    private Stage stage;

    @ScriptMark(type = NodeType.Complex)
    private Steps steps;

    @ScriptMark(type = NodeType.Complex, name = "stage")
    private List<Stage> stageList;

//    @ScriptMark(type = NodeType.Complex, name = "stages")
//    private List<Stages> stagesList;

    private final static List<String> BUILD_IN_KEYWORDS = Arrays.asList(new String[]{"name"});

    public String toScript() {
        return toScript(0);
    }

    private ScriptNode toNode(Class<? extends ParentNode> clazz, Map.Entry<String, PropertyDescriptor> entry) {
        ScriptMark annotation = BeanSugar.PropertyDescriptors.getAnnotationIncludeParents(clazz, entry.getValue(), ScriptMark.class);
        PropertyDescriptor propertyDescriptor = entry.getValue();
        NodeType nodeType = annotation == null ? NodeType.Complex : annotation.type();
        String nodeName = (annotation == null || !StringUtils.hasText(annotation.name())) ? propertyDescriptor.getName() : annotation.name();
        Object nodeObject = BeanSugar.PropertyDescriptors.getValue(this, propertyDescriptor);
        ScriptNode node = new ScriptNode(nodeType, nodeName, nodeObject);
        return node;
    }

    public String toScript(int level) {
        Map<String, PropertyDescriptor> map = BeanSugar.PropertyDescriptors.toNamePropertyMapExceptNull(this);
        StringBuilder sb = new StringBuilder();
        Class<? extends ParentNode> clazz = this.getClass();
        for (Map.Entry<String, PropertyDescriptor> entry : map.entrySet()) {
            ScriptNode node = toNode(clazz, entry);
            if (BUILD_IN_KEYWORDS.contains(node.getName()))
                continue;

            fillNodeScript(sb, node, level);
        }

        String script = sb.toString();
        return script;
    }

    private void fillNodeScript(StringBuilder sb, ScriptNode node, int level) {
        String name = node.getName();
        NodeType type = node.getType();
        Object object = node.getObject();

        switch (type) {
            case Complex: {
                if (object instanceof ParentNode) {
                    String nodeObjectName = getName(object);
                    String nodeObjectMutateName = getMutateName(nodeObjectName);
                    wrapLine(sb, level, MessageFormat.format("{0}{1} '{'", name, nodeObjectMutateName));

                    ParentNode value = ((ParentNode) object);
                    wrapNode(sb, level + 1, value);

                    wrapLine(sb, level, "}");
                } else if (object instanceof List){
                    List<ParentNode> nodeList = (List<ParentNode>) object;
                    for (ParentNode nodeItem : nodeList) {
                        String itemName = getName(nodeItem);
                        String itemMutateName = getMutateName(itemName);

                        wrapLine(sb, level, MessageFormat.format("{0}{1} '{'", name, itemMutateName));
                        wrapNode(sb, level + 1, nodeItem);
                        wrapLine(sb, level, "}");
                    }
                }
                break;
            }
            case Simple:{
                wrapLine(sb, level, MessageFormat.format("{0} {1} ", name, object));
                break;
            }
            case Block:{
                if (object instanceof String) {
                    wrapLine(sb, level, formatBlock());
                } else
                    throw new RuntimeException(MessageFormat.format("Cannot cast object(name:{0}) to 'String'.", name));
                break;
            }
            case Ignore:{
                break;
            }
        }
    }

    private String formatBlock() {
        String block = getBlock();
        String formatBlock = StringSugar.Linux.setVariable(block, this);
        return formatBlock;
    }

    private static String getMutateName(String name) {
        return StringUtils.hasText(name) ? MessageFormat.format("(''{0}'')", name) : Constant.EMPTY;
    }

    private static String getName(Object itemValue) {
        return BeanSugar.PropertyDescriptors.exist(itemValue, "name") ? (String) BeanSugar.PropertyDescriptors.getValue(itemValue, "name") : null;
    }

    private static void wrapLine(StringBuilder sb, int level, String line) {
        boolean multipleLine = line.contains(Constant.LINE_SEPARATOR) ? (line.split(Constant.LINE_SEPARATOR).length > 1 ? true : false) : false;
        if (multipleLine) {
            String[] split = line.split(Constant.LINE_SEPARATOR);
            for (String item : split) {
                sb.append(nLevelBlank(level) + item + Constant.LINE_SEPARATOR);
            }
        } else
            sb.append(nLevelBlank(level) + line + Constant.LINE_SEPARATOR);
    }

    private static void wrapNode(StringBuilder sb, int level, ParentNode node) {
        sb.append(node.toScript(level));
    }

    public static String nLevelBlank(int level) {
        String blank = "  ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(blank);
        }
        return sb.toString();
    }







    private final static String PLACE_HOLDER = "\\$\\'{'{0}\\'}'";

    private final static String placeHolder(String parameter) {
        return MessageFormat.format(PLACE_HOLDER, parameter);
    }
}
