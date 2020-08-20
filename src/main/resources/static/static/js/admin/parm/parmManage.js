var editObj = null, ptable = null, treeGrid = null, tableId = 'treeTable', layer = null;
layui.config({
    base: '/static/ext/treeGird/design/extend/'
}).extend({
    treeGrid: 'treeGrid'
}).use(['jquery', 'treeGrid', 'layer'], function () {

    var $ = layui.jquery;
    treeGrid = layui.treeGrid;//很重要
    layer = layui.layer;
    ptable = treeGrid.render({
        id: tableId
        , elem: '#' + tableId
        , idField: 'id'
        , url: '/admin/perm/getPermList'
        , cellMinWidth: 100
        , treeId: 'id'//树形id字段名称
        , treeUpId: 'pid'//树形父id字段名称
        , treeShowName: 'title'//以树形式显示的字段
        , cols: [[
            {field: 'id', width: 50, title: 'id'}
            , {field: 'pid', width: 50, title: 'pid'}
            , {field: 'title', width: 300, title: '名称'}

            , {
                field: 'available',width: 80, title: '是否可用', align: 'center', templet: function (d) {
                    if (d.type == "permission") {
                        if (d.available == 1) {
                            return '<span class="layui-blue">可用</span>'
                        } else {
                            return '<span class="layui-red">不可用</span>'
                        }
                    } else {
                        return "";
                    }
                }
            }
            , {field: 'percode', width: 170, title: '权限名称'}
            ,{width:150,title: '操作', align:'center'/*toolbar: '#barDemo'*/
                ,templet: function(d){
                    if (d.type == "permission") {
                        var html='';
                        var addBtn='<button type="button" lay-event="edit" class="layui-btn layui-btn-sm"><span class="layui-icon layui-icon-edit"></span>更新</button>';
                        var delBtn='<button type="button" lay-event="del" class="layui-btn layui-btn-sm layui-btn-danger"><span class="layui-icon layui-icon-delete"></span>删除</button>';
                        return addBtn+delBtn;
                    }
                    else
                    {
                        return "";
                    }
                }
            }
        ]]
        , height: 300
        , page: false
    });
    $(document).on('click', '#btn', function () {
        console.log(treeGrid)
        treeGrid.query(tableId);
        layer.msg('hello');
    });

    $("#add").click(function () {
        openAddLayer();
    })


    treeGrid.on('tool(' + tableId + ')', function (obj) {
        var data = obj.data; //获得当前行数据
        if (obj.event === 'del') {//删除行
            del(data);
        } else if (obj.event === "edit") {//添加行
            openEditLayer(data);
        }
    });

    function del(data) {
        layer.confirm("你确定删除数据吗？", {icon: 3, title: '提示'},
            function (index) {//确定回调
                console.log(data)
                var obj = {id: data.id};
                $.post("/admin/menu/delMenu", obj, function (res) {
                    if (res.code == 200) {
                        layer.msg("删除成功");
                        treeGrid.query(tableId);
                    } else {
                        layer.msg(res.msg);
                    }
                });
                layer.close(index);
            }, function (index) {//取消回调
                layer.close(index);
            }
        );
    }

    var mainIndex;
    var url;

    //打开添加的弹出层
    function openAddLayer() {
        mainIndex = layer.open({
            type: 2,
            content: '/admin/perm/addPerm',
            area: ['800px', '400px'],
            title: '添加权限',
            success: function () {

            }
        });
    }

    //打开添加的弹出层
    function openEditLayer(edit) {
        mainIndex = layer.open({
            type: 2,
            content: '/admin/perm/editPerm?id=' + edit.id,
            area: ['800px', '400px'],
            title: '添加菜单',
            success: function () {

            }
        });
    }
});


var i = 1000;

