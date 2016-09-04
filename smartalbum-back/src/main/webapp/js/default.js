function htmlEscape(uneString){
	uneString = replaceAll(uneString,"&amp;", "%26");
	uneString = replaceAll(uneString,"&", "%26");
	uneString = replaceAll(uneString,"?", "%3F");
	uneString = replaceAll(uneString,"#", "%23");
	uneString = replaceAll(uneString,"=", "%3D");
	
	return uneString;
}


function htmlUnescape(uneString){
	
	uneString = replaceAll(uneString,"&amp;", "&");
	uneString = replaceAll(uneString,"&gt;", ">");
	uneString = replaceAll(uneString,"&lt;", "<");
	
	return unescape(uneString);
}


function replaceAll(text, strA, strB){
    while(text.indexOf(strA) != -1){
        text = text.replace(strA, strB);
    }
	
    return text;
}


function isInt(num){
	var exp = new RegExp("^[0-9-.]*$","g");
	return exp.test(num);
}

function getKeyCode(e){
	var keyCode;
    
    if(e && e.which){
    	keyCode = e.which;
    }else if(window.event){
        e = window.event;
        keyCode = e.keyCode;
    }
    
    return keyCode;
}

function isEnterPressed(e){
	return (getKeyCode(e) == 13);
}