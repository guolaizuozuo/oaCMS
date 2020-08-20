layui.extend({
    dtree: '/static/ext/dtree/dtree'
}).use(['form','table','layer','dtree'],function(){
    var form = layui.form;
	var table = layui.table;
    var dtree=layui.dtree;
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    var rid=$("#rid").val();

    //根据角色ID请求权限和菜单tree的json数据
    dtree.render({
        elem: "#permissionTree",
        url: "/admin/role/initPermissionByRoleId?roleId="+rid,
        dataStyle: "layuiStyle",  //使用layui风格的数据格式
        dataFormat: "list",  //配置data的风格为list
        width:400,
        response:{message:"msg",statusCode:0},  //修改response中返回数据的定义
        checkbar: true,
        checkbarType: "all" // 默认就是all，其他的值为： no-all  p-casc   self  only
    });

    $("#savePerm").on("click",function(){

        var permissionData = dtree.getCheckbarNodesParam("permissionTree");

        var params="rid="+rid;
        console.log(permissionData);
        $.each(permissionData,function(index,item){
            params+="&ids="+item.nodeId;
        });
        $.post("/admin/role/saveRolePermission",params,function(res){
            //top.layer.msg("设置完成！");
            parent.layer.closeAll(); //再执行关闭
            layer.msg(res.msg);
        })
        return false;
    });
})
