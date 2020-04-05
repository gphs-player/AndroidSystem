### 使用方法区别

#### 第一种

```java
        //OK 返回的是rootView
        View view = View.inflate(this, R.layout.layout_inflate, root);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_inflate, root);
        LayoutInflater.from(this).inflate(R.layout.layout_inflate, root,true);
       
```

以上三种方法都是将inflate出的View添加到root布局，返回的结果也是root根布局，如果再次调用addView方法将会报错

```java
 //The specified child already has a parent. You must call removeView() on the child's parent first.
        root.addView(view);
```

#### 第二种

```java
View view = LayoutInflater.from(this).inflate(R.layout.layout_inflate, root, false);
```

因为attachToRoot表示的是false，所以不会将inflate的View添加到root，返回的就是layout_inflate的布局。

#### 第三种

```java
View view = LayoutInflater.from(this).inflate(R.layout.layout_inflate, null);
```

如果root为null，那么inflate的View是没有params的

### 源码

```java
public View inflate(XmlPullParser parser, @Nullable ViewGroup root, boolean attachToRoot) {
    synchronized (mConstructorArgs) {

        final Context inflaterContext = mContext;
        final AttributeSet attrs = Xml.asAttributeSet(parser);
        Context lastContext = (Context) mConstructorArgs[0];
        mConstructorArgs[0] = inflaterContext;
        View result = root;//返回值首先确定为root根View

        try {
            int type;
            while ((type = parser.next()) != XmlPullParser.START_TAG &&
                    type != XmlPullParser.END_DOCUMENT) {
            }
            if (type != XmlPullParser.START_TAG) {//解析出错
                throw new InflateException(parser.getPositionDescription()
                        + ": No start tag found!");
            }
            final String name = parser.getName();
            if (TAG_MERGE.equals(name)) {//Merge标签的判断
                if (root == null || !attachToRoot) {
                    throw new InflateException("<merge /> can be used only with a valid "
                            + "ViewGroup root and attachToRoot=true");
                }
                rInflate(parser, root, inflaterContext, attrs, false);
            } else {
                //创建具体的View
                final View temp = createViewFromTag(root, name, inflaterContext, attrs);
                ViewGroup.LayoutParams params = null;
                if (root != null) {//
                    // 只有在root！=null的时候才会赋值params
                    params = root.generateLayoutParams(attrs);
                    if (!attachToRoot) {//不添加到root结点的话就设置一个params
                        // Set the layout params for temp if we are not
                        // attaching. (If we are, we use addView, below)
                        temp.setLayoutParams(params);
                    }
                }
                rInflateChildren(parser, temp, attrs, true);
							//满足两个条件
                if (root != null && attachToRoot) {//将inflate的View添加到root根布局
                    root.addView(temp, params);
                }
							//如果attachToRoot是false，返回值就是子View，通常需要手动addView
                if (root == null || !attachToRoot) {
                    result = temp;
                }
            }
        } catch (Exception e) {
            ......
        } finally {
            // Don't retain static reference on context.
            mConstructorArgs[0] = lastContext;
            mConstructorArgs[1] = null;
        }
        return result;
    }
}
```