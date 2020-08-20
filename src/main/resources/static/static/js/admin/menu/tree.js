var editObj=null,ptable=null,treeGrid=null,tableId='treeTable',layer=null;
layui.config({
    base: '/static/ext/treeGird/design/extend/'
}).extend({
    treeGrid:'treeGrid'
}).use(['jquery','treeGrid','layer'], function(){

    var $=layui.jquery;
    treeGrid = layui.treeGrid;//很重要
    layer=layui.layer;
    ptable=treeGrid.render({
        id:tableId
        ,elem: '#'+tableId
        ,idField:'id'
        ,url:'/admin/menu/getMenu'
        ,cellMinWidth: 100
        ,treeId:'id'//树形id字段名称
        ,treeUpId:'pid'//树形父id字段名称
        ,treeShowName:'title'//以树形式显示的字段
        ,cols: [[
             {field:'id',width:50, title: 'id'}
            ,{field:'pid', width:50,title: 'pid'}
            ,{field:'title',width:260, title: '名称'}
            ,{field:'type',width:100, title: '类型',templet:function (d) {
                    var url = d.type;
                    if(d.type == "menu"){
                        url = "菜单";
                    }
                    else
                    {
                        url = "按钮";
                    }
                    return url;

                }}
            ,{field:'icon',width:50, title: '图标',templet:function (d) {
                    return '<font class=layui-icon>'+d.icon+'</font>';
                }}

            ,{field:'href',width:250, title: '链接地址'}
            ,{fixed: 'right', title:'操作', toolbar: '#menuRowBar',align:'center',width:'150'}
        ]]
        ,height:300
        ,page:false
    });
    $(document).on('click','#btn',function () {
        console.log(treeGrid)
        treeGrid.query(tableId);
        layer.msg('hello');
    });

    $("#add").click(function(){
        openAddLayer();
    })


    treeGrid.on('tool('+tableId+')',function (obj) {
        var data = obj.data; //获得当前行数据
        if(obj.event === 'del'){//删除行
             del(data);
        }else if(obj.event==="edit"){//添加行
            openEditLayer(data);
        }
    });

    function del(data) {
        layer.confirm("你确定删除数据吗？", {icon: 3, title:'提示'},
            function(index){//确定回调
            console.log(data)
                var obj={id:data.id};
                $.post("/admin/menu/delMenu",obj,function (res) {
                    if(res.code==200)
                    {
                        layer.msg("删除成功");
                        treeGrid.query(tableId);
                    }
                    else
                    {
                        layer.msg(res.msg);
                    }
                });
                layer.close(index);
            },function (index) {//取消回调
                layer.close(index);
            }
        );
    }

    var mainIndex;
    var url;
    //打开添加的弹出层
    function openAddLayer(){
        mainIndex=layer.open({
            type:2,
            content:'/admin/menu/addMenu',
            area:['800px','500px'],
            title:'添加菜单',
            success:function(){

            }
        });
    }

    //打开添加的弹出层
    function openEditLayer(edit){
        mainIndex=layer.open({
            type:2,
            content:'/admin/menu/editMenu?id='+edit.id,
            area:['800px','500px'],
            title:'添加菜单',
            success:function(){

            }
        });
    }
});


var i=1000;

