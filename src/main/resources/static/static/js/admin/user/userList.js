layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#newsList',
        url : '/admin/user/loadAllUser',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "newsListTable",
        cols : [[
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'imgpath', title: '头像', width:300},
            {field: 'name', title: '姓名', width:100},
            {field: 'loginname', title: '登录名', align:'center'},
            {field: 'deptname', title: '部门', align:'center'},
            {field: 'available', title: '状态',  align:'center',templet:"#newsStatus"},

            {field: 'newsTop', title: '是否置顶', align:'center', templet:function(d){
                return '<input type="checkbox" name="newsTop" lay-filter="newsTop" lay-skin="switch" lay-text="是|否" '+d.newsTop+'>'
            }},
            {field: 'hiredate', title: '添加时间', align:'center', minWidth:110, templet:function(d){
                return d.hiredate.substring(0,10);
            }},
            {title: '操作', width:400, templet:'#newsListBar',fixed:"right",align:"center"}
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

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){

            table.reload("newsListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    name: $(".searchVal").val()  //搜索的关键字
                }
            })

    });

    //分配角色
    function selectRole(data){
        var url="/admin/role/selectRole?id="+data.id;

        var index = layui.layer.open({
            title : "分配角色",
            type : 2,
            area: ['800px', '500px'],
            content : url,
            success : function(layero, index){

            },
            end:function () {
            }
        })
    }
    //添加
    function addUser(data){
        var url;
        if(data)
        {
            url="/admin/user/userEdit?id="+data.id;
        }
        else
        {  url="/admin/user/userAdd";
        }
        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            area: ['900px', '500px'],
            content : url,
            success : function(layero, index){

            },
            end:function () {
                // tableIns.reload({
                //     where:data.field,
                //     page:{
                //         curr:1
                //     }
                // });
            }
        })
    }
    $(".addNews_btn").click(function(){
        addUser();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(newsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'update'){ //编辑
            addUser(data);
        }else if(layEvent === 'resetPwd'){ //重置密码
            layer.confirm('确定重置此用户密码？',{icon:3, title:'提示信息'},function(index){
                 $.get("/admin/user/resetPwd",{
                     id : data.id
                 },function(res){
                     layer.alert(res.msg)
                 })
            });
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                    tableIns.reload();
                    layer.close(index);
                // })
            });
        } else if(layEvent === 'selectRole'){ //预览
            selectRole(data);
        }
    });

})