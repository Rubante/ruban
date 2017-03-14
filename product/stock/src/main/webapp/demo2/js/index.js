$(function() {
	$(".hdtop_weixin").hover(function() {
		$(this).children(".wx").show()
	});
	$(".hdtop_weixin").mouseleave(function() {
		$(this).children(".wx").hide()
	});
});
function getByClass(oParent, sClass) {
	var aEle = oParent.getElementsByTagName("*");
	var arrs = [];
	var re = new RegExp("\\b" + sClass + "\\b", "i");
	for (var i = 0; i < aEle.length; i++) {
		if (re.test(aEle[i].className)) {
			arrs.push(aEle[i])
		}
	}
	return arrs
}
function tab(id, clsNav, tag, clsCon, sEvent) {
	var id = document.getElementById(id);
	var btns = getByClass(id, clsNav)[0].getElementsByTagName(tag);
	var cons = getByClass(id, clsCon);
	for (var i = 0; i < btns.length; i++) {
		btns[i].index = i;
		btns[i][sEvent] = function() {
			for (var i = 0; i < btns.length; i++) {
				btns[i].className = "";
				cons[i].style.display = "none"
			}
			this.className = "select";
			cons[this.index].style.display = "block"
		}
	}
};