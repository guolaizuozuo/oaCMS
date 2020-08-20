layui.use(['form', 'layer'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    form.on("submit(addUser)", function (data) {
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        // 实际使用时的提交信息
        $.post("/admin/user/userEdit", data.field, function (res) {

            if (res.code == 200) {
                setTimeout(function () {
                   // top.layer.close(index);
                    top.layer.msg("用户添加成功！");
                    //top.layer.closeAll("iframe");
                    parent.layer.closeAll(); //再执行关闭
                    //刷新父页面
                    parent.layui.table.reload('newsListTable');
                    //parent.location.reload();
                }, 1000);
            } else {
                top.layer.msg(res.msg);
            }
        })

        return false;
    })

    $.get("/admin/dept/loadDept",function (res) {
        var users=res.data;
        var dom_mgr=$("#deptid");
        dom_mgr.empty();
        var html="<option value='0'>请选择部门</option>";
        $.each(users,function(index,item){
            html+="<option value='"+item.id+"'>"+item.title+"</option>";
        });
        dom_mgr.html(html);
        //重新渲染
        dom_mgr.val(dom_mgr.attr("data"));
        form.render("select");
    });


    //自定义验证规则
    form.verify({
        checknumber: function(value){
           // var currentnumber=parseInt($("#currentnumber").val());
            if(parseInt(value)==0){
                return "请选择部门";
            }
        }
    });
});