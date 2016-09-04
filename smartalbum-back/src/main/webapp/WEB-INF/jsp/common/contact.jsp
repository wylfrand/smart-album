	<!-- Main -->
	<div id="main">
		<div class="center">
			
			<!-- Content -->
			<div id="content" class="col col-2">
				<div class="entry">
					<h5 class="title">Contact</h5>
					<form action="#" class="validate-form" method="post">
						<div class="msg-alert">
							<p><strong>Error!</strong> Please fill in all required fields!</p>
						</div>
						<div class="msg-thanks">
							<p><strong>Thank You!</strong> Your message was sent!</p>
						</div>
						<div class="row">
							<label>First Name<span>(required)</span></label>
							<input type="text" id="fname-field" name="fname-field" class="field required text-field" />
							<div class="cl">&nbsp;</div>
						</div>
						<div class="row">
							<label>Last Name<span>(required)</span></label>
							<input type="text" id="lname-field" name="lname-field" class="field required text-field" />
							<div class="cl">&nbsp;</div>
						</div>
						<div class="row">
							<label>Your Email<span> (required) Will not be published</span></label>
							<input type="text" id="mail-field" name="mail-field" class="field required valid-email text-field" />
							<div class="cl">&nbsp;</div>
						</div>
						<div class="row">
							<label>Company</label>
							<input type="text" id="company-field" class="field text-field" name="company-field" />
							<div class="cl">&nbsp;</div>
						</div>
						<div class="row">
							<label>Message<span>(required)</span></label>
							<textarea id="message-field" name="message-field" class="field message required text-field" cols="100" rows="5"></textarea>
							<div class="cl">&nbsp;</div>
						</div>
						<div class="row">
						<security:authorize access="isAuthenticated()">
							<input type="submit" class="submit-btn" value="Send the Message" />
						</security:authorize>
							<div class="cl">&nbsp;</div>
						</div>
					</form>
				</div>
			</div>
			<!-- End Content -->
			
			<!-- Sidebar -->
			<div id="sidebar" class="col last">
				
				<div class="entry">
					<h5 class="title">Address</h5>
					<p>Inspire ltd Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
					<ul class="contact-info">
					    <li><strong>Email:</strong> <a href="#">info@inspire.com</a></li>
					    <li><strong>Phone:</strong> <span class="phone-number">655-606-605</span></li>
					    <li><strong>Adress:</strong> East Pixel Bld. 99, Creative City 9000 Republic of Design</li>
					</ul>
					<img src="css/images/google-map.gif" alt="" class="google-map" />
					<p>Netbased ltd dolor sit amet, consectetur adipiscing elit. Integer dictum, neque ut consectetur adipiscing elit. Integer dictum, neque ut imperdiet pellentesque, nulla tellus tempus magna.</p>
				</div>
				
				<div class="entry">
					<h5 class="title">Advertisment</h5>
					<a href="http://htmlmafia.com" class="ads"><img src="css/images/htmlmafia.jpg" alt="" /></a>
					<a href="http://mailbakery.com" class="ads"><img src="css/images/mailbakery.jpg" alt="" /></a>
				</div>
				
			</div>
			<!-- End Sidebar -->
			
			<div class="cl">&nbsp;</div>
		</div>
	</div>
	<!-- End Main -->
	<!-- Footer Push -->
	<div id="footer-push" class="notext">&nbsp;</div>
	<!-- End Footer Push -->
