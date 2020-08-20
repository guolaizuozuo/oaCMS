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
        url : '/admin/role/loadAllRole',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        id : "roleListTable",
        cols : [ [
            {field:'id', title:'ID',align:'center'}
            ,{field:'name', title:'角色名称',align:'center'}
            ,{field:'remark', title:'角色备注',align:'center'}
            ,{field:'available', title:'是否可用',align:'center',templet:function(d){
                    return d.available==1?'<font color=blue>可用</font>':'<font color=red>不可用</font>';
                }}
            ,{field:'createtime', title:'创建时间',align:'center'}
            ,{fixed: 'right', title:'操作', toolbar: '#roleRowBar',align:'center',width:'300'}
        ] ]
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

            table.reload("roleListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    name: $(".searchVal").val()  //搜索的关键字
                }
            })

    });

    //分配角色
    function selectPerm(data){
        var url="/admin/role/selectPermPage?rid="+data.id;

        var index = layui.layer.open({
            title : "分配权限",
            type : 2,
            area: ['500px', '500px'],
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


    //列表操作
    table.on('tool(newsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'update'){ //编辑
           alert("还未完成")
        }else if(layEvent === 'resetPwd'){ //重置密码
            layer.confirm('确定重置此用户密码？',{icon:3, title:'提示信息'},function(index){
                 $.get("/admin/user/resetPwd",{
                     id : data.id
                 },function(res){
                     layer.alert(res.msg)
                 })
            });
        } else if(layEvent === 'delete'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                    tableIns.reload();
                    layer.close(index);
                // })
            });
        } else if(layEvent === 'selectRole'){ //预览
            selectPerm(data);
        }
    });

})