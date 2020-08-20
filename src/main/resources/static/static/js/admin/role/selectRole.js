layui.use(['table','layer'],function(){
	var table = layui.table;
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    var userId=$("#id").val();
    table.render({
        elem: '#logs',
        url : '/admin/role/initRoleByUserId?id='+userId,
        cellMinWidth : 95,
        page : false,
        height : "full-150",
        limit : 20,
        limits : [10,15,20,25],
        id : "roleTable",
        cols : [[
            {type:'checkbox',align:'center'}
            ,{field:'id', title:'ID',align:'center'}
            ,{field:'name', title:'角色名称',align:'center'}
            ,{field:'remark', title:'角色备注',align:'center'}
        ]]
    });

    $(".assignment").on("click",function(){
        var checkStatus = table.checkStatus('roleTable');
        var params="uid="+userId;
        $.each(checkStatus.data,function(index,item){
            params+="&ids="+item.id;
        });

        $.post("/admin/user/saveUserRole", params, function (res) {

            if (res.code == 200) {
                setTimeout(function () {
                    // top.layer.close(index);
                    top.layer.msg("设置完成！");
                    //top.layer.closeAll("iframe");
                    parent.layer.closeAll(); //再执行关闭
                    //刷新父页面
                   // parent.layui.table.reload('newsListTable');
                    //parent.location.reload();
                }, 1000);
            } else {
                top.layer.msg(res.msg);
            }
        })
return false;
    });

})
