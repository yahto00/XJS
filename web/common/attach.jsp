<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件管理</title>
<script type="text/javascript">
	var attachGrid = null;
	var pbar = null;
	function ooxx(){
		alert(1);
	}
	function getAttachSelectRecordIds(){
		var records = attachGrid.getSelectionModel().getSelections();
		if(records.join('')=='') {
	    	return false;
	    }
	    var ids = [];
	    for(var i=0; i<records.length; i+=1){
	    	var id = records[i].get('id');  
	        ids.push(id);                //向数组后添加元素
	    }
		 return ids.toString();
	}
	function refreshAttachGrid(attachGroupId){
		Ext.Ajax.request({ 
 	 		url:'${base}/admin/getAttachList.do',
 	 		params:{attachGroupId : attachGroupId},
 	        success : function(res ,options) {
 	 			var objs= Ext.decode(res.responseText);
 	 			attachGrid.getStore().proxy = new Ext.data.PagingMemoryProxy(objs); 
 	 			attachGrid.getStore().load({params:{start:0,limit:20}});
 			}, 
 	        failure : function(response) { 
 				Ext.tooltip.msg('no', '系统繁忙，请稍后再试！');
 	        }
 		}); 
	}
	
	function read(){
		alert(1);
	}
	
	function viewAttach(attachGroupId){
		var attachRecord = Ext.data.Record.create([
       		{name:'id',type:'int'},
       		{name:'name',type:'String'},
       		{name:'type',type:'int'},
       		{name:'url',type:'string'},
       		{name:'submitDate',type:'string'},
       		{name:'attachGroup.id',type:'int'},
       		{name:'remarks',type:'string'}
		]);
		var attachStore = new Ext.data.Store({
     	 	reader:new Ext.data.JsonReader({},attachRecord)
     	});
		var p_rm = new Ext.grid.RowNumberer();
       	var p_sm = new Ext.grid.CheckboxSelectionModel();
       	var p_cm = new Ext.grid.ColumnModel([
			p_rm,p_sm,
			{header:'附件名称',dataIndex:'name',width:80,sortable : true},
			{	header:'附件类型',
      	 		width:20,
          	 	dataIndex:'type',
          		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
        		if(value == 0){
        			return '图片';
        		}else if(value == 1){
        			return '文档';
        		}else if(value == 2){
        			return '视频';
        		}else if(value == 3){
        			return '音频';
        		}else if(value == 4){
        			return '其它';
	        	}
        			return '';
          		},
          		sortable : true
	        },
            {header:'备注',dataIndex:'remarks',width:50,sortable : true},
            {header:'在线阅读',dataIndex:'',width:50,renderer:function(){
            	return '<div onclick="javascript:read();">read</div>';
            }},
            {header:'下载',
            	dataIndex:'',
    			sortable:true,
    			width:20,
    			menuDisabled : true,
    			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
            		return  '<img  src="${base}/images/icons/download.gif" style="cursor: pointer" onclick="downloadAttach('+record.get("id")+');"/>';
    			},
    			align:'center'
    		}
 	    ]);
       	attachGrid = new Ext.grid.GridPanel({
       		region:'center',
      		height:700,
      		stripeRows: true,
      		loadMask:true,
      		columnLines: true,
      		store:attachStore,
      		cm: p_cm,
      		sm: p_sm,
      		viewConfig:{
      			columnsText:'显示的列',
      		    scrollOffset:-1,//滚动条宽度,默认20
      		    sortAscText:'升序',
      		    sortDescText:'降序',
      		    forceFit:true//表格会自动延展每列的长度,使内容填满整个表格
      		},
      		listeners:{
       			rowdblclick: function(){
       				viewAttachManage();
       			}
       		},
      		tbar :  new Ext.Toolbar({
    			height: 40,
    			style:'padding:10px 10px 0px 10px;',
    			items:[
    	   	    	'附件名称：',
    	   	    	new Ext.form.TextField({
    		    		id:'name'
    		    	}),
    	   	    	{
    	       	    	text :   "查询", 
    	       	    	iconCls: "search-button", 
    	       	    	handler:function(){
    	   	    			attachStore.filterBy( function(record){
            	    			var realName = Ext.getCmp('name').getValue();
            	    			if(realName == ''){
                	    			return true;
            	    			}else {
            	    				if(record.get('name').indexOf($.trim(realName))==-1){
            	    					return false;
            	    				}
            	    				return true;
                	    		}
                	    	});
    	       			} 
    	       	   	},
    	   		    '-',
    	   	    	{
    	       	    	text:"重置", 
    	       	    	iconCls: "reset-button",
    	       	    	handler:function(){
    	       	   			Ext.getCmp('name').setValue("");
    	       	   		    attachStore.load();
    	       			} 
    	       	   	},'->'
    	       	   	,
    	       	 	{
    	       	   		text : "上传",
        	    		iconCls: "add-button",
        	    		handler: function(){
        	    			uploadAttach(attachGroupId);
        	    		}
    	    	   	},
    	       	 	'-',
    	   	    	{
    	    	   		text : "修改",
    	    	    	iconCls: "update-button",
    	    	    	handler: function(){
    	    	    		updateAttach(attachGroupId);
    	    	    	}
    	       	   	},
    	       	 	'-',
    	   	    	{
    	    	   		text : "删除",
    	    	    	iconCls: "delete-button",
    	    	    	handler: function(){
    	    	    		deleteAttach(attachGroupId);
    	    	    	}
    	       	   	}
    	       	]
       		})/* , 
      		bbar:new Ext.PagingToolbar({
      	    	pageSize: 20,
      	        store: attachStore,
      	        displayInfo: true,
      	        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
      	        emptyMsg: "无数据",
      	       	plugins: new Ext.ux.ProgressBarPager()
      	    }),
      	    plugins:new Ext.ux.PanelResizer({
      	        minHeight: 100
      	    }) */
		});
       	var attachWin = new Ext.Window({
     		title:'附件管理',
     	    closable:true,
     	    modal:true,
	        plain:true,
	        border:false,
     	    width:700,
     	    height:400,
     	    layout :"fit",
     	   resizable:false,
     	    items: [
				attachGrid
     	    ],
     	    buttonAlign:'center', 
     	    buttons:[{
     	    	text:'关闭',
     	    	handler:function(){
     	    		attachWin.close();
     	    	}
     	 	}]
     	});
       	Ext.Ajax.request({ 
 	 		url:'${base}/admin/getAttachList.do',
 	 		params:{attachGroupId:attachGroupId},
 	        success : function(res ,options) {
 	 			var objs= Ext.decode(res.responseText);
 	 			attachGrid.getStore().proxy = new Ext.data.PagingMemoryProxy(objs); 
 	 			attachGrid.getStore().load({params:{start:0,limit:20}});
 			}, 
 	        failure : function(response) { 
 				Ext.tooltip.msg('no', '系统繁忙，请稍后再试！');
 	        }
 		}); 
       	attachWin.show();
	}
	
	function uploadAttach(attachGroupId){
		//添加上传附件窗口
		var addAttachForm = new Ext.form.FormPanel({
			bodyStyle :'padding:20px 5px 0 0',
        	border :false,
        	layout : 'form', 
    	    labelAlign:'right',
    	    fileUpload : true,
    	    labelWidth:80,
    	    defaults:{
    	      	width:250,
    	      	allowBlank:false
    	    },
    	    items:[{
    	    	xtype:'hidden',
		        name:'attach.attachGroup.id',
		      	value : attachGroupId
    	    },{
				xtype : 'textfield',
				id : 'txtAddNewsManageAttach',
				name : 'attachFile',
				inputType : 'file',
				fieldLabel : '上传附件',
				listeners : {
					'render':function(){
						var newsManageAttachCmp = Ext.get('txtAddNewsManageAttach');
						
						newsManageAttachCmp.on('change',function(field,newValue,oldValue){
					        var url = '';
					        var txtNewsManageAttach = Ext.get('txtAddNewsManageAttach').dom;
							if (window.navigator.userAgent.indexOf("MSIE")>=1){
								txtNewsManageAttach.select();
								url = document.selection.createRange().text; 
					     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
					     		if(txtLawManageAttach.files){
					     			url = txtNewsManageAttach.files.item(0).getAsDataURL();
					     		}
					     		url = txtNewsManageAttach.value;
					     	}

					     	if(url == ''){
								return;
						    }
					     	if(url != ''){
					     		var attachName = newsManageAttachCmp.dom.value;
								attachName = attachName.substring(attachName.lastIndexOf("\\") + 1);
								attachName = attachName.substring(0,attachName.lastIndexOf("."));
								var txtAttachName = addAttachForm.getForm().findField("attach.name");
								txtAttachName.setValue(attachName);
						     }
						},this);
			    	}
				}
			},{
    	    	xtype : 'textfield',
    	    	fieldLabel:'附件名称',
			        name:'attach.name',
			        blankText : '附件名称不能为空'
			},{
		    	fieldLabel:'附件类型',
		        hiddenName:'attach.type',
		        xtype:'combo',emptyText:'-----请选择-----',
		        store:new Ext.data.SimpleStore({
		           fields:['value','text'],
		           data:[[0,'图片'],[1,'文档'],[2,'视频'],[3,'音频'],[4,'其它']]
		        }),
		        emptyText:'请选择',
		        mode:'local',
		        triggerAction:'all',
		        displayField:'text',
		        valueField:'value',
		        typeAhead : true,
		        value : 1,   
		        blankText : '附件类型不能为空',   
		        selectOnFocus : true,   
		        editable : false
			},{
    	    	xtype : 'textarea',
    	        fieldLabel:'附件描述',
    	        name:'attach.remarks',
    	        height : 100,
    	        allowBlank:true
    	    }]
    	});
		var addAttachWin = new Ext.Window({
			title: '上传附件',
	        closable:true,
	        width:420,
	        height:300,
	        modal:true,
	        plain:true,
	        layout :"fit",
	        resizable:false,
	        items: [
				addAttachForm
	        ],
	        buttonAlign:'center', 
	    	buttons:[{
	    		text:'保存',
	    	    handler:function(){
		    		if(!addAttachForm.getForm().isValid()){
	        	    	return;
	        	    }
		    		waitUpload();
		    		//验证附件格式
		    		var url = '';
			        var txtNewsManageAttach = Ext.get('txtAddNewsManageAttach').dom;
					if (window.navigator.userAgent.indexOf("MSIE")>=1){
						txtNewsManageAttach.select();
						url = document.selection.createRange().text; 
			     	}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
			     		if(txtNewsManageAttach.files){
			     			url = txtNewsManageAttach.files.item(0).getAsDataURL();
			     		}
			     		url = txtNewsManageAttach.value;
			     	}
		    		addAttachForm.getForm().submit({
		            	url:'${base}/admin/uploadAttach.do',
		                success:function(form,action){
				            Ext.getCmp("waitUploadWin").close();
		                	var message = action.result.msg; 
		                	if(message == "success"){
		                		showProgressBar(attachGroupId);
		                	}
		                	addAttachWin.close();
		                	refreshAttachGrid(attachGroupId);
	              	    },
		              	failure:function(form,action){
				            Ext.getCmp("waitUploadWin").close();
	              	    	Ext.tooltip.msg('no', action.result.msg);
		              	}
	                });
	    		}
    	    },{
    	       	text:'重置',
    	       	handler : function(){
    	    		addAttachForm.getForm().reset();
    	    	}
    	    },{
    	       	text:'关闭',
    	       	handler:function(){
    	    		addAttachWin.close();
    	    	}
	    	}]
	    });
		addAttachWin.show();
	}
	
      function updateAttach(attachGroupId){
		 //判断是否只选择一行
	   	var ids = getAttachSelectRecordIds();
		if(ids==false){
			Ext.tooltip.msg('no', '请选择要操作的行！');
         	return;
		}if(ids.indexOf(',')!=-1){
			Ext.tooltip.msg('no', '只能选择一条记录！');
         	return;
		}
		
		var row = attachGrid.getSelectionModel().getSelections()[0];
		var updateAttachForm = new Ext.form.FormPanel({
			bodyStyle :'padding:20px 5px 0 0',
	       	border :false,
	       	layout : 'form', 
	   	    labelAlign:'right',
	   	    fileUpload : true,
	   	    labelWidth:80,
	   	    defaults:{
	   	      	width:250,
	   	      	allowBlank:false
	   	    },
	   	    items:[{
	   	    	xtype:'hidden',
			        name:'attach.id',
			      	value : row.get('id')
	   	    },{
	   	    	xtype : 'textfield',
	   	        fieldLabel:'附件名称',
	   	        name:'attach.name',
	   	        value: row.get('name'),
	   	        allowBlank:true
	       	},{
			    	fieldLabel:'附件类型',
			        hiddenName:'attach.type',
			        xtype:'combo',emptyText:'-----请选择-----',
			        store:new Ext.data.SimpleStore({
			           fields:['value','text'],
			           data:[[0,'图片'],[1,'文档'],[2,'视频'],[3,'音频'],[4,'其它']]
			        }),
			        emptyText:'请选择',
			        mode:'local',
			        triggerAction:'all',
			        displayField:'text',
			        valueField:'value',
			        typeAhead : true,
			        value : row.get('type'),   
			        blankText : '附件类型不能为空',   
			        selectOnFocus : true,   
			        editable : false
			    },{
	   	    	xtype : 'textarea',
	   	        fieldLabel:'附件描述',
	   	        name:'attach.remarks',
	   	        value: row.get('remarks'),
	   	        height : 100,
	   	        allowBlank:true
	   	    }]
		});
		var updateAttachWin = new Ext.Window({
			title: '修改附件',
	        closable:true,
	        width:410,
	        height:280,
	        modal:true,
	        plain:true,
	        layout :"fit",
	        resizable:false,
	        items: [
				updateAttachForm
	        ],
	        buttonAlign:'center', 
	    	buttons:[{
	    		text:'保存',
	    	    handler:function(){
		    		if(!updateAttachForm.getForm().isValid()){
	        	    	return;
	        	    }
		    		updateAttachForm.getForm().submit({
		            	url:'${base}/admin/updateAttach.do',
		                success:function(form,action){
		                	Ext.tooltip.msg('yes', action.result.msg);
		                	updateAttachWin.close();
		                	refreshAttachGrid(attachGroupId);
	              	    },
		              	failure:function(form,action){
	              	    	Ext.tooltip.msg('no', action.result.msg);
		              	}
	                });
	    		}
   	    },{
   	       	text:'重置',
   	       	handler : function(){
   	    		updateAttachForm.getForm().reset();
   	    	}
   	    },{
   	       	text:'关闭',
   	       	handler:function(){
   	    		updateAttachWin.close();
   	    	}
	    	}]
	    });
		updateAttachWin.show();
	}
      
    function deleteAttach(attachGroupId){
			var ids = getAttachSelectRecordIds();//所选择删除行的ID
			  if(ids==false){
	  	        Ext.tooltip.msg('no', '请选择要操作的行！');
	  	       	return
	  	    }
	 		Ext.MessageBox.confirm('信息提示：', '确定要删除这'+attachGrid.getSelectionModel().getSelections().length+'条数据?', function(btn){
     			if(btn == 'yes'){
     	    		Ext.Ajax.request({
	       				url: '${base}/admin/deleteAttach.do',
	       	    		params: { ids: ids},
	       	    		success: function(response, opts) {
	  	       	    		var result = Ext.decode(response.responseText);
	   	       	    		var flag = result.success;
				 	 		if(flag){
	   	       	    			Ext.tooltip.msg('yes', result.msg);
	   	       	    			refreshAttachGrid(attachGroupId);
				 	     	}else{
				 	      		Ext.tooltip.msg('no', result.msg);
					 	 	}
	       	    		}
     	    		});
     	    	}
     		});
	}
    
   function waitUpload() {
		new Ext.Window({
			id:'waitUploadWin',
	        closable:false,
	        width:260,
	        height:120,
	        border:false,
	        bodyBorder:false,
	        frame:true,
	        closeAction:'close',
	        modal:true,
	        plain:false,
	        hideBorders:true,
  		    resizable:false,
	        layout :"fit",
			bodyStyle: 'background-color: white;',
			html:'<center><img src="${base}/images/waitImg.gif" style="margin-top:8px;"></img><br><span style="font-size:13px; margin-top:5px; color:#FF6A6A">正在预读取您的文件，请稍等...</span></center>'
		}).show();
	}
   
   function showProgressBar(attachGroupId) {
		pbar = new Ext.ProgressBar({	//实例化进度条
			width:480			    //进度条的宽度
		});
		var progressBarWin = new Ext.Window({
			id:'progressBarWin',
			title: '上传进度',
	        closable:false,
	        width:500,
	        height:85,
	        closeAction:'close',
	        plain:false,
 		    resizable:false,
	        layout :"fit",
			items:[
				pbar
			],
			bodyStyle: 'background-color: white;',
			buttonAlign:'center', 
	    	buttons:[{
	    		text:'取消',
	    	    handler:function(){
	    			Ext.Ajax.request({ 
	    	 	 		url:'${base}/admin/cancelUpload.do',
	    	 	        success : function(res ,options) {
	    	 	        	Ext.tooltip.msg('yes', '已经取消文件上传！');
	    	 	        	refreshAttachGrid(attachGroupId);
	    	 	        	Ext.getCmp("progressBarWin").close();
	    	 	        	return;
	    	 	        }, 
	    	 	        failure : function(response) { 
	    	 	        	Ext.tooltip.msg('on', '取消上传文件失败！');
	    	 	        }
	    	 		}); 
	    		}
	    	}]
		});
		progressBarWin.show();
		updateProgressBar();
	}
   
   function updateProgressBar() {
		var progress;
		Ext.Ajax.request({ 
	 		url:'${base}/admin/updateProgressBar.do',
	        success : function(res ,options) {
	 			progress = Ext.decode(res.responseText);
	 			if(progress.msg == 100) {
	 				pbar.updateProgress(progress.msg/100);
	 				pbar.updateText("进度:"+progress.msg+"%");
	 				Ext.Ajax.request({ 
	 		 	 		url:'${base}/admin/uploadSuccess.do',
	 		 	        success : function(res ,options) {
	 		 	        	Ext.getCmp("progressBarWin").close();
	 		 	        	Ext.tooltip.msg('yes', '上传文件成功！');
	 		 	        },
	 		 	        failure : function(response) { 
	 		 	        }
	 		 		}); 
					return;
	 			}
				pbar.updateProgress(progress.msg/100);
				pbar.updateText("进度:"+progress.msg+"%");
				setTimeout("updateProgressBar()",2);
	        }, 
	        failure : function(response) { 
	        	alert("上传文件失败！");
				Ext.tooltip.msg('no', '上传过程中出现问题，请取消上传！');
	        }
		}); 
	}
   
   function viewAttachManage(){
		var row = attachGrid.getSelectionModel().getSelections()[0];
		var type = row.get('type');
		if(type == 0){
			type = '图片';
		}else if(type == 1){
			type ='文档';
		}else if(type == 2){
			type ='视频';
		}else if(type == 3){
			type ='音频';
		}else if(type == 4){
			type ='其它';
   	}
		var tpl = new Ext.XTemplate( 
				'<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;font-family: \'宋体\', \'sans-serif\'; border:0px;margin:0px;line-height:23px;">',
				'<tr>',
				'<td width="25%" style="border:1px dotted #999;text-align:right; padding-right:5px;padding-top:5px;padding-bottom:5px;">',
			    '附件名称：', 
				'</td>',
				'<td width="75%" style="border-top: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;padding-top:5px;padding-bottom:5px;">',
			    '{name}&nbsp;', 
				'</td>',
				'</tr>',
				'<tr>',
				'<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;padding-top:5px;padding-bottom:5px;">',
			    '附件类型：', 
			    '</td>',
				'<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;padding-top:5px;padding-bottom:5px;">',
			    '{type}&nbsp;', 
			    '</td>',
				'</tr>',
				'<tr>',
				'<td valign="top" style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;padding-top:5px;padding-bottom:5px;">',
			    '备注：', 
			    '</td>',
				'<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;padding-top:5px;padding-bottom:5px;">',
			    '{remarks}&nbsp;', 
			    '</td>',
				'</tr>',
				'<table>' 
			); 
		var viewAttachWin = new Ext.Window({
			title: '查看附件',
			resizable:false,
	        closable:true,
	        width:420,
	        height:270,
	        modal:true,
	        plain:false,
	        layout :"fit",
	        autoScroll : true,
	        bodyStyle: 'background-color: white;',
	        buttonAlign:'center', 
	    	buttons:[{
   	       	text:'关闭',
   	       	handler:function(){
	    			viewAttachWin.close();
   	    	}
	    	}]
	    });
		viewAttachWin.show();
		var data = new Object();
		data.name = row.get('name');
		data.type = type;
		data.remarks = row.get('remarks');
		tpl.overwrite(viewAttachWin.body, data); 
	}
   
   function downloadAttach(attachId){
		window.location.href = 'downloadFile.do?attachId='+attachId;
	}
</script>
</head>
<body>
</body>
</html>