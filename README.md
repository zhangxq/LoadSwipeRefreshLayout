android官方提供的 SwipeRefreshLayout 非常美观实用，但是只有下拉刷新功能，我们项目中一般都是下拉刷新和上拉加载更多同时使用的。

本文将介绍一套工具集，继承自官方 SwipeRefreshLayout， 实现上拉加载更多，并且兼容ListView 和 RecyclerView 以及 RecyclerView 多列网格样式


ListView 和 RecyclerView效果一致，如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190412172953211.gif)

RecyclerView 多列网格样式如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190412173244338.gif)

## 使用方式
refreshLayout.setOnLoadListener(this); 

一般在onCreate方法中调用。

## 接口介绍
setOnLoadListener：设置加载更多监听器。

setLoadEnable：设置是否可以加载更多，**设置加载更多监听器之后会默认开启**，一般当加载完全部数据时关闭，下拉刷新后开启。

setAutoLoad：设置列表滚动到底部时是否自动加载更多，默认开启。

setLoading：用于数据加载完成后关闭加载动画。

setCrollListener（仅限ListRefreshLayout）：由于需要监听列表滚动，所以ListRefreshLayout占用了滚动监听器，用户可以在这里设置监听器，接收由ListRefreshLayout监听并且回调出来的滚动事件。

## 代码简介
核心类分为三个，基类RefreshLayout，两个子类ListRefreshLayout，RecyclerRefreshLayout。

ListRefreshLayout用于实现ListView的加载更多。

RecyclerRefreshLayout和LoadRecyclerAdapter配合，用于实现RecyclerView（包含网格形式）的加载更多。
由于ListView和RecyclerView设置滚动监听方式，判断是否滚动底部方式以及设置加载更多动画的方式不一样，所以，RefreshLayout通过虚方法将这些任务交给两个子类分别实现，RefreshLayout实现了核心的加载更多监听设置以及回调，是否加载更多开关的设置，上滑手势的判断。

## LoadRecyclerAdapter使用方法
RecyclerRefreshLayout和LoadRecyclerAdapter需要配合使用，RecyclerView必须设置一个继承自LoadRecyclerAdapter的适配器才能正常使用加载更多功能。

LoadRecyclerAdapter使用方法，实现两个虚方法：
onCreateItemViewHolder：使用方式同onCreateViewHolder。
onBindItemViewHolder：使用方式同onBindViewHolder。

设置数据个数：
setDataSize，当数据发生改变，及时设置数据个数。
目前来看，可以满足大部分需求。


源码使用方式：直接copy loadrefreshlayout模块里的6个类或者接口到自己的项目即可，或者直接导入loadrefreshlayout模块。
