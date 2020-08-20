layui.use(['treeSelect', 'form', 'layer'], function () {
    var treeSelect = layui.treeSelect;
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    treeSelect.render({
        // 选择器
        elem: '#tree',
        // 数据
        data: '/admin/menu/getTreeSelectMenu',
        // 异步加载方式：get/post，默认get
        type: 'get',
        // 占位符
        placeholder: '请选择上级父节点',
        // 是否开启搜索功能：true/false，默认false
        search: true,
        // 点击回调
        click: function (d) {
            $("#pid").val(d.current.id);
        },
        // 加载完成后的回调函数
        success: function (d) {
            // 选中节点，根据id筛选
            if ($("#pid").val() != "")
                treeSelect.checkNode('tree', $("#pid").val());
        }
    });

    form.on("submit(addMenu)", function (data) {
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        // 实际使用时的提交信息
        $.post("/admin/perm/addPerm", data.field, function (res) {

            if (res.code == 200) {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("权限添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 1000);
            } else {
                top.layer.msg(res.msg);
            }
        })

        return false;
    })


});