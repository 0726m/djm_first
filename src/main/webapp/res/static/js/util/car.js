layui.define(['layer'],function(exports){
	var layer = layui.layer;
	
var car = {
  init : function(){
      var uls = document.getElementById('list-cont').getElementsByTagName('ul');//每一行
      var SelectedPieces = document.getElementsByClassName('Selected-pieces')[0];//总件数
      var piecesTotal = document.getElementsByClassName('pieces-total')[0];//总价
      var batchdeletion = document.getElementsByClassName('batch-deletion')[0]//批量删除按钮
      //计算
      function getTotal(){
          var seleted = 0,price = 0;
          for(var i = 0; i < uls.length;i++){
                seleted += parseInt(uls[i].getElementsByClassName('Quantity-input')[0].value);
                price += parseFloat(uls[i].getElementsByClassName('sum')[0].innerHTML);
      		}
          SelectedPieces.innerHTML = seleted;
          piecesTotal.innerHTML = price.toFixed(2);
      }

      function fn1(){
        alert(1)
      }
      // 小计
      function getSubTotal(ul){
        var unitprice = parseFloat(ul.getElementsByClassName('th-su')[0].innerHTML);
        var count = parseInt(ul.getElementsByClassName('Quantity-input')[0].value);
        var SubTotal = parseFloat(unitprice*count)
        ul.getElementsByClassName('sum')[0].innerHTML = SubTotal.toFixed(2);
      }

      for(var i = 0; i < uls.length;i++){
        uls[i].onclick = function(e){
          e = e || window.event;
          var el = e.srcElement;
          var cls = el.className;
          var input = this.getElementsByClassName('Quantity-input')[0];
          var stockinput = this.getElementsByClassName('Stock-input')[0];
          var less = this.getElementsByClassName('less')[0];
          var val = parseInt(input.value);
          var stock = parseInt(stockinput.value);
          var that = this;
          switch(cls){
            case 'add layui-btn':
	            if(val<stock){
	              input.value = val + 1;
	            }else{
	            	alert("库存不足");
	            }
              getSubTotal(this);
              getTotal();
              break;
            case 'less layui-btn':
              if(val > 1){
                input.value = val - 1;
              }
              getSubTotal(this);
              getTotal();
              break;
          }
        }
      }
      //加载计算
      getTotal();
  	  }
  }

  exports('car',car)
}); 