layui.use(['form','layer','laydate','table','laytpl','laydate'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;
    var laydate=layui.laydate;
    //初始化时间选择器
    laydate.render({
        elem:'#startTime',
        type:'datetime'
    });
    laydate.render({
        elem:'#endTime',
        type:'datetime'
    })

    //新闻列表
    var tableIns = table.render({
        elem: '#newsList',
        url : '/admin/log/loadAllLog',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "newsListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'username', title: '操作人', width:100},
            {field: 'operation', title: '日志类型', width:100},
            {field: 'time', title: '耗时', align:'center',width:60},
            {field: 'params', title: '运行参数', align:'center'},
            {field: 'ip', title: 'ip', align:'center',width:100},
            {field: 'location', title: '地点', align:'center',width:160},
            {field: 'createTime', title: '添加时间', align:'center', minWidth:110, templet:function(d){
                return d.createTime.substring(0,19);
            }}
        ]]
    });

    //是否置顶
    form.on('switch(newsTop)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            if(data.elem.checked){
                layer.msg("置顶成功！");
            }else{
                layer.msg("取消置顶成功！");
            }
        },500);
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            var obj={ids:newsId};
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                 $.get("删除文章接口",obj,function(data){
                tableIns.reload();
                layer.close(index);
                 })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

//模糊查询
    form.on("submit(doSearch)",function(data){
        tableIns.reload({
            where:data.field,
            page:{
                curr:1
            }
        });
        return false;
    });
})