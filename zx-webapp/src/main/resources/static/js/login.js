$(document).ready(function(e) {
	if($("#register_in").val().length>0){
		alert($("#register_in").val());
	}
	
	var width = window.outerWidth;
	var height = screen.height-(window.outerHeight - window.innerHeight);
	var  e = 2.718281828459;    
	var dd_margin_left = 0.0955;
	

<!--   用户登录   start -->
$("#login").css("width","350px").css("height","470px").css("top",1/2*(height-500)+"px").css("left",1/2*(width-350)+"px");
$("#login_top").css("width","350px").css("height","50px").css("line-height","50px");
$("#apart1").css("width","350px").css("height","60px");
$("#apart2").css("width","350px").css("height","24px");
$("#apart3").css("width","350px").css("height","15px");
$("#apart4").css("width","350px").css("height","60px");
$("#apart5").css("width","350px").css("height","24px");
$("#apart6").css("width","350px").css("height","24px");
$("#user_area").css("width","320px").css("height","42px").css("margin-left","12.5px");
$("#pwd_area").css("width","320px").css("height","42px").css("margin-left","12.5px");
$("#login_area").css("width","320px").css("height","42px").css("margin-left","12.5px");

$("#in").focus(function(){
	$(this).css("outline","none");
	});
$("#pwd_in").focus(function(){
	$(this).css("outline","none");
	});

$("#username").css("width","320px").css("height","40px").css("line-height","37px");
$("#password").css("width","320px").css("height","40px").css("line-height","37px");
$("#login_now").css("width","320px").css("height","40px").css("line-height","37px");

$("#checkBox_area").css("width","320px").css("height","30px").css("line-height","30px").css("margin-left","12.5px");
$("#reb_pwd").css("width","90px").css("height","30px");
$("#auto_login").css("width","90px").css("height","30px");


$("#footer").css("width","350px").css("height","35px").css("line-height","35px");
$("#time").css("width","350px").css("height","45px").css("line-height","45px");

$("#forget").click(function(){
	alert("此功能尚未完善");
	});
	
$("#in").blur(function(){$('#user_area').css('border','1px solid #CCC');});
$("#in").focus(function(){$('#user_area').css('border','1px solid #0CF');});
$("#pwd_in").blur(function(){$('#pwd_area').css('border','1px solid #CCC');});
$("#pwd_in").focus(function(){$('#pwd_area').css('border','1px solid #0CF');});

<!--   用户登录   end -->
		


<!-- 更改登录背景  start -->
$("#change_bg").change(function(){
		$("body").css("background-image","url(../img/background-img/"+$(this).val()+".jpg")
})
<!-- 更改登录背景  end -->



$("#login_a").click(function(){
	$("#login_form").submit();
})





/*显示七彩动态泡泡效果*/	
function Particle(x, y, radius) {
	this.init(x, y, radius);
}
Particle.prototype = {
	init : function(x, y, radius) {
		this.alive = true;
		this.radius = radius || 10;
		this.wander = 0.15;
		this.theta = random(TWO_PI);
		this.drag = 0.92;
		this.color = '#fff';
		this.x = x || 0.0;
		this.y = y || 0.0;
		this.vx = 0.0;
		this.vy = 0.0;
	},
	move : function() {
		this.x += this.vx;
		this.y += this.vy;
		this.vx *= this.drag;
		this.vy *= this.drag;
		this.theta += random(-0.5, 0.5) * this.wander;
		this.vx += sin(this.theta) * 0.1;
		this.vy += cos(this.theta) * 0.1;
		this.radius *= 0.96;
		this.alive = this.radius > 0.5;
	},
	draw : function(ctx) {
		ctx.beginPath();
		ctx.arc(this.x, this.y, this.radius, 0, TWO_PI);
		ctx.fillStyle = this.color;
		ctx.fill();
	}
};

var MAX_PARTICLES = 280;
var COLOURS = [ '#0CF', '#A7DBD8', '#E0E4CC', '#F38630', '#FA6900',
		'#FF4E50', '#F9D423' ,'green','red','blue'];
var particles = [];
var pool = [];
var demo = Sketch.create({
	container : document.getElementById('main')
});
demo.setup = function() {
	var i, x, y;

	x = (demo.width * 0.3) + random(-100, 100);
	y = (demo.height * 0.3) + random(-100, 100);
	demo.spawn(0, 999);

};
demo.spawn = function(x, y) {
	if (particles.length >= MAX_PARTICLES)
		pool.push(particles.shift());
	particle = pool.length ? pool.pop() : new Particle();
	particle.init(x, y, random(5, 40));
	particle.wander = random(0.5, 2.0);
	particle.color = random(COLOURS);
	particle.drag = random(0.9, 0.99);
	theta = random(TWO_PI);
	force = random(2, 8);
	particle.vx = sin(theta) * force;
	particle.vy = cos(theta) * force;
	particles.push(particle);
};
demo.update = function() {
	var i, particle;
	for (i = particles.length - 1; i >= 0; i--) {
		particle = particles[i];
		if (particle.alive)
			particle.move();
		else
			pool.push(particles.splice(i, 1)[0]);
	}
};
demo.draw = function() {
	demo.globalCompositeOperation = 'lighter';
	for ( var i = particles.length - 1; i >= 0; i--) {
		particles[i].draw(demo);
	}
};
demo.mousemove = function() {
	var particle, theta, force, touch, max, i, j, n;
	for (i = 0, n = demo.touches.length; i < n; i++) {
		touch = demo.touches[i], max = random(1, 4);
		for (j = 0; j < max; j++) {
			demo.spawn(touch.x, touch.y);
		}
	}
};

});