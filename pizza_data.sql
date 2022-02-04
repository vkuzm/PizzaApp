create table blog(
    post_id bigint not null primary key,
    created_at        varchar(255),
    description       varchar(10000),
    image             varchar(255),
    short_description varchar(500),
    title             varchar(255)
);

alter table blog owner to postgres;

create table comments(
    id bigint not null primary key,
    created_at varchar(255),
    email      varchar(255),
    message    varchar(500),
    name       varchar(255),
    post_id    bigint not null
        constraint fks4rbna18vmp09nl71emqywmgy
            references blog
);

alter table comments owner to postgres;

create table pages (
    id bigint not null primary key,
    created_at        timestamp,
    description       varchar(10000),
    h1                varchar(255),
    href              varchar(255),
    short_description varchar(1000),
    title             varchar(255)
);

alter table pages owner to postgres;

create table orders(
    order_id bigint not null primary key,
    comment         varchar(50),
    email           varchar(255),
    first_name      varchar(20),
    last_name       varchar(20),
    order_date      varchar(255),
    payment_method  varchar(255),
    shipping_method varchar(255),
    subtotal        integer,
    total           integer
);

alter table orders owner to postgres;

create table order_product (
    id bigint  not null primary key,
    price        integer not null,
    product_name varchar(255),
    quantity     integer not null,
    total        integer not null,
    order_id     bigint  not null
        constraint fkl5mnj9n0di7k1v90yxnthkc73
            references orders
);

alter table order_product owner to postgres;

create table custom_fields(
    id bigint  not null primary key,
    content    varchar(10000),
    field_name varchar(255),
    sort       integer not null
        constraint custom_fields_sort_check
            check (sort >= 0)
);

alter table custom_fields owner to postgres;

create table slider (
    id bigint  not null primary key,
    background_image boolean not null,
    description      varchar(1000),
    image            varchar(255),
    justify_center   boolean not null,
    order_button     boolean not null,
    product_id       integer default 0,
    styles           varchar(255),
    subtitle         varchar(255),
    title            varchar(255),
    view_button      boolean not null
);

alter table slider owner to postgres;

create table categories (
    id bigint  not null primary key,
    created_at  varchar(255),
    description varchar(10000),
    name        varchar(255),
    sort        integer not null
);

alter table categories owner to postgres;

create table products(
    id bigint not null primary key,
    created_at        varchar(255),
    description       varchar(10000),
    hot               boolean,
    image             varchar(255),
    name              varchar(255),
    price             integer,
    short_description varchar(500),
    category_id       bigint
        constraint fkog2rp4qthbtt2lfyhfo32lsw9
            references categories
);

alter table products owner to postgres;

INSERT INTO public.orders (order_id, comment, email, first_name, last_name, order_date, payment_method, shipping_method, subtotal, total) VALUES (33, 'dads', 'email@dsa.as', 'Max', 'Black', '12/12/2018', 'cash', 'courier', 13, 13);
INSERT INTO public.orders (order_id, comment, email, first_name, last_name, order_date, payment_method, shipping_method, subtotal, total) VALUES (62, 'comment', 'email@dsa.as', 'Alex', 'Black', null, 'credit_card', 'pickup', 9, 9);
INSERT INTO public.orders (order_id, comment, email, first_name, last_name, order_date, payment_method, shipping_method, subtotal, total) VALUES (83, 'dsad', 'test@asd.ru', 'dsad', 'dasd', null, 'credit_card', 'pickup', 6, 6);
INSERT INTO public.orders (order_id, comment, email, first_name, last_name, order_date, payment_method, shipping_method, subtotal, total) VALUES (99, 'dsadadasdasd', 'test@com.com', 'Test', 'test', null, 'credit_card', 'pickup', 28, 28);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (34, 2, 'Italian Pizza', 1, 2, 33);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (35, 3, 'Tomatoe Pie', 1, 3, 33);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (36, 6, 'Caucasian Pizza', 1, 6, 33);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (63, 3, 'Tomatoe Pie', 1, 3, 62);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (64, 6, 'Caucasian Pizza', 1, 6, 62);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (84, 2, 'Italian Pizza', 1, 2, 83);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (85, 4, 'American Pizza', 1, 4, 83);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (100, 10, 'Pizza Cheese', 1, 10, 99);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (101, 2, 'Italian Pizza', 1, 2, 99);
INSERT INTO public.order_product (id, price, product_name, quantity, total, order_id) VALUES (102, 4, 'American Pizza', 4, 16, 99);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (3, '<h3>Open Monday-Friday</h3><p>8:00am - 9:00pm', 'header_contact_work_time', 3);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (4, '#twitter', 'social_twitter', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (5, '#facebook', 'social_facebook', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (6, '#instagram', 'social_instagram', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (7, ' <div class="heading-section ftco-animate ">
      <h2 class="mb-4">Welcome to <span class="flaticon-pizza">Pizza</span> A Restaurant</h2>
    </div>
    <div>
      <p>On her way she met a copy. The copy warned the Little Blind Text, that where it came from it would have been
        rewritten a thousand times and everything that was left from its origin would be the word "and" and the Little
        Blind Text should turn around and return to its own, safe country. But nothing the copy said could convince her
        and so it didn’t take long until a few insidious Copy Writers ambushed her, made her drunk with Longe and Parole
        and dragged her into their agency, where they abused her for their.</p>
    </div>', 'about', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (8, '<div class="row justify-content-center mb-5 pb-3">
      <div class="col-md-7 heading-section ftco-animate text-center">
        <h2 class="mb-4">Our Services</h2>
        <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind
          texts.</p>
      </div>
    </div>
    <div class="row">
      <div class="col-md-4 ftco-animate">
        <div class="media d-block text-center block-6 services">
          <div class="icon d-flex justify-content-center align-items-center mb-5">
            <span class="flaticon-diet"></span>
          </div>
          <div class="media-body">
            <h3 class="heading">Healthy Foods</h3>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
          </div>
        </div>
      </div>
      <div class="col-md-4 ftco-animate">
        <div class="media d-block text-center block-6 services">
          <div class="icon d-flex justify-content-center align-items-center mb-5">
            <span class="flaticon-bicycle"></span>
          </div>
          <div class="media-body">
            <h3 class="heading">Fastest Delivery</h3>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
          </div>
        </div>
      </div>
      <div class="col-md-4 ftco-animate">
        <div class="media d-block text-center block-6 services">
          <div class="icon d-flex justify-content-center align-items-center mb-5"><span class="flaticon-pizza-1"></span>
          </div>
          <div class="media-body">
            <h3 class="heading">Original Recipes</h3>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
          </div>
        </div>
      </div>
    </div>', 'our_services', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (10, '<h2 class="ftco-heading-2">About Us</h2>
            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the
              blind texts.</p>', 'footer_about', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (11, '203 Fake St. Mountain View, San Francisco, California, USA', 'address', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (12, '+2 392 3929 210', 'phone', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (13, 'info@yourdomain.com', 'email', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (14, 'Pizza<br><small>Delicous</small>', 'title', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (15, '55,44', 'map_coord', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (16, '2', 'map_key', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (9, '		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate fadeInUp ftco-animated">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<div class="icon"><span class="flaticon-pizza-1"></span></div>
		              	<strong class="number" data-number="100">100</strong>
		              	<span>Pizza Branches</span>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate fadeInUp ftco-animated">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<div class="icon"><span class="flaticon-medal"></span></div>
		              	<strong class="number" data-number="85">85</strong>
		              	<span>Number of Awards</span>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate fadeInUp ftco-animated">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<div class="icon"><span class="flaticon-laugh"></span></div>
		              	<strong class="number" data-number="10567">10,567</strong>
		              	<span>Happy Customer</span>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate fadeInUp ftco-animated">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<div class="icon"><span class="flaticon-chef"></span></div>
		              	<strong class="number" data-number="900">900</strong>
		              	<span>Staff</span>
		              </div>
		            </div>
		          </div>
', 'statistics', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (22, '<h3>000 (123) 456 7890</h3>
<p>A small river named Duden flows</p>', 'header_contact_phone', 0);
INSERT INTO public.custom_fields (id, content, field_name, sort) VALUES (21, '<h3>198 West 21th Street</h3>
<p>Suite 721 New York NY 10016</p>', 'header_contact_address', 0);
INSERT INTO public.slider (id, background_image, description, image, justify_center, order_button, product_id, styles, subtitle, title, view_button) VALUES (2, false, null, 'http://localhost:8080/common-service/images/bg_2.png', false, true, 0, 'order-md-last', 'Crunchy', 'Italian Pizza', true);
INSERT INTO public.slider (id, background_image, description, image, justify_center, order_button, product_id, styles, subtitle, title, view_button) VALUES (3, true, null, 'http://localhost:8080/common-service/images/bg_3.jpg', true, true, 0, 'text-center', 'Welcome', 'We cooked your desired Pizza Recipe', true);
INSERT INTO public.slider (id, background_image, description, image, justify_center, order_button, product_id, styles, subtitle, title, view_button) VALUES (1, false, null, 'http://localhost:8080/common-service/images/bg_1.png', false, true, 0, null, 'Delicious', 'Italian Cuizine', true);
INSERT INTO public.blog (post_id, created_at, description, image, short_description, title) VALUES (7, '07/04/2019', '
				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis, eius mollitia suscipit, quisquam doloremque distinctio perferendis et doloribus unde architecto optio laboriosam porro adipisci sapiente officiis nemo accusamus ad praesentium? Esse minima nisi et. Dolore perferendis, enim praesentium omnis, iste doloremque quia officia optio deserunt molestiae voluptates soluta architecto tempora.</p>
				<p>
				  <img src="http://localhost:8082/images/image_1.jpg" alt="" class="img-fluid">
				</p>
				<p>Molestiae cupiditate inventore animi, maxime sapiente optio, illo est nemo veritatis repellat sunt doloribus nesciunt! Minima laborum magni reiciendis qui voluptate quisquam voluptatem soluta illo eum ullam incidunt rem assumenda eveniet eaque sequi deleniti tenetur dolore amet fugit perspiciatis ipsa, odit. Nesciunt dolor minima esse vero ut ea, repudiandae suscipit!</p>
				<h2 class="mb-3 mt-5">#2. Creative WordPress Themes</h2>
				<p>Temporibus ad error suscipit exercitationem hic molestiae totam obcaecati rerum, eius aut, in. Exercitationem atque quidem tempora maiores ex architecto voluptatum aut officia doloremque. Error dolore voluptas, omnis molestias odio dignissimos culpa ex earum nisi consequatur quos odit quasi repellat qui officiis reiciendis incidunt hic non? Debitis commodi aut, adipisci.</p>
				<p>
				  <img src="http://localhost:8082/images/image_2.jpg" alt="" class="img-fluid">
				</p>
				<p>Quisquam esse aliquam fuga distinctio, quidem delectus veritatis reiciendis. Nihil explicabo quod, est eos ipsum. Unde aut non tenetur tempore, nisi culpa voluptate maiores officiis quis vel ab consectetur suscipit veritatis nulla quos quia aspernatur perferendis, libero sint. Error, velit, porro. Deserunt minus, quibusdam iste enim veniam, modi rem maiores.</p>
				<p>Odit voluptatibus, eveniet vel nihil cum ullam dolores laborum, quo velit commodi rerum eum quidem pariatur! Quia fuga iste tenetur, ipsa vel nisi in dolorum consequatur, veritatis porro explicabo soluta commodi libero voluptatem similique id quidem? Blanditiis voluptates aperiam non magni. Reprehenderit nobis odit inventore, quia laboriosam harum excepturi ea.</p>
				<p>Adipisci vero culpa, eius nobis soluta. Dolore, maxime ullam ipsam quidem, dolor distinctio similique asperiores voluptas enim, exercitationem ratione aut adipisci modi quod quibusdam iusto, voluptates beatae iure nemo itaque laborum. Consequuntur et pariatur totam fuga eligendi vero dolorum provident. Voluptatibus, veritatis. Beatae numquam nam ab voluptatibus culpa, tenetur recusandae!</p>
				<p>Voluptas dolores dignissimos dolorum temporibus, autem aliquam ducimus at officia adipisci quasi nemo a perspiciatis provident magni laboriosam repudiandae iure iusto commodi debitis est blanditiis alias laborum sint dolore. Dolores, iure, reprehenderit. Error provident, pariatur cupiditate soluta doloremque aut ratione. Harum voluptates mollitia illo minus praesentium, rerum ipsa debitis, inventore?</p>', 'http://localhost:8080/information-service/images/image_3.jpg', 'A small river named Duden flows by their place and supplies it with the necessary regelialia.', '10 Tips For The Travele');
INSERT INTO public.blog (post_id, created_at, description, image, short_description, title) VALUES (8, '07/02/2019', 'A small river named Duden flows by their place and supplies it with the necessary regelialia. A small river named Duden flows by their place and supplies it with the necessary regelialia. A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'http://localhost:8080/information-service/images/image_3.jpg', 'A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'The Delicious Pizza');
INSERT INTO public.blog (post_id, created_at, description, image, short_description, title) VALUES (6, '07/02/2019', '<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis, eius mollitia suscipit, quisquam
          doloremque distinctio perferendis et doloribus unde architecto optio laboriosam porro adipisci sapiente
          officiis nemo accusamus ad praesentium? Esse minima nisi et. Dolore perferendis, enim praesentium omnis, iste
          doloremque quia officia optio deserunt molestiae voluptates soluta architecto tempora.</p>
        <p>
          <img src="http://localhost:8080/images/image_1.jpg" alt="" style="max-height:300px; margin:0 auto" class="img-fluid">
        </p>
        <p>Molestiae cupiditate inventore animi, maxime sapiente optio, illo est nemo veritatis repellat sunt doloribus
          nesciunt! Minima laborum magni reiciendis qui voluptate quisquam voluptatem soluta illo eum ullam incidunt rem
          assumenda eveniet eaque sequi deleniti tenetur dolore amet fugit perspiciatis ipsa, odit. Nesciunt dolor
          minima esse vero ut ea, repudiandae suscipit!</p>
        <h2 class="mb-3 mt-5">#2. Creative WordPress Themes</h2>
        <p>Temporibus ad error suscipit exercitationem hic molestiae totam obcaecati rerum, eius aut, in. Exercitationem
          atque quidem tempora maiores ex architecto voluptatum aut officia doloremque. Error dolore voluptas, omnis
          molestias odio dignissimos culpa ex earum nisi consequatur quos odit quasi repellat qui officiis reiciendis
          incidunt hic non? Debitis commodi aut, adipisci.</p>
        <p>
          <img src="http://localhost:8080/images/image_2.jpg" style="max-height:300px; margin:0 auto" alt="" class="img-fluid">
        </p>
        <p>Quisquam esse aliquam fuga distinctio, quidem delectus veritatis reiciendis. Nihil explicabo quod, est eos
          ipsum. Unde aut non tenetur tempore, nisi culpa voluptate maiores officiis quis vel ab consectetur suscipit
          veritatis nulla quos quia aspernatur perferendis, libero sint. Error, velit, porro. Deserunt minus, quibusdam
          iste enim veniam, modi rem maiores.</p>
        <p>Odit voluptatibus, eveniet vel nihil cum ullam dolores laborum, quo velit commodi rerum eum quidem pariatur!
          Quia fuga iste tenetur, ipsa vel nisi in dolorum consequatur, veritatis porro explicabo soluta commodi libero
          voluptatem similique id quidem? Blanditiis voluptates aperiam non magni. Reprehenderit nobis odit inventore,
          quia laboriosam harum excepturi ea.</p>
        <p>Adipisci vero culpa, eius nobis soluta. Dolore, maxime ullam ipsam quidem, dolor distinctio similique
          asperiores voluptas enim, exercitationem ratione aut adipisci modi quod quibusdam iusto, voluptates beatae
          iure nemo itaque laborum. Consequuntur et pariatur totam fuga eligendi vero dolorum provident. Voluptatibus,
          veritatis. Beatae numquam nam ab voluptatibus culpa, tenetur recusandae!</p>
        <p>Voluptas dolores dignissimos dolorum temporibus, autem aliquam ducimus at officia adipisci quasi nemo a
          perspiciatis provident magni laboriosam repudiandae iure iusto commodi debitis est blanditiis alias laborum
          sint dolore. Dolores, iure, reprehenderit. Error provident, pariatur cupiditate soluta doloremque aut ratione.
          Harum voluptates mollitia illo minus praesentium, rerum ipsa debitis, inventore?</p>', 'http://localhost:8080/information-service/images/image_1.jpg', 'A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'The Delicious Pizza');
INSERT INTO public.blog (post_id, created_at, description, image, short_description, title) VALUES (11, '07/02/2019', 'A small river named Duden flows by their place and supplies it with the necessary regelialia. A small river named Duden flows by their place and supplies it with the necessary regelialia. A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'http://localhost:8080/information-service/images/image_3.jpg', 'A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'The Delicious Pizza');
INSERT INTO public.blog (post_id, created_at, description, image, short_description, title) VALUES (10, '07/02/2019', 'A small river named Duden flows by their place and supplies it with the necessary regelialia. A small river named Duden flows by their place and supplies it with the necessary regelialia. A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'http://localhost:8080/information-service/images/image_2.jpg', 'A small river named Duden flows by their place and supplies it with the necessary regelialia.', 'The Delicious Pizza');
INSERT INTO public.comments (id, created_at, email, message, name, post_id) VALUES (32, '07/11/2019', 'email@dsa.as', 'This is first comment!', 'Nick', 7);
INSERT INTO public.comments (id, created_at, email, message, name, post_id) VALUES (33, '08/11/2019', 'adasd@dsa.ru', 'This is second comment!', 'Alex', 7);
INSERT INTO public.comments (id, created_at, email, message, name, post_id) VALUES (34, '08/11/2019', 'adasd@dsa.ru', 'This is third comment!
', 'Mijek', 7);
INSERT INTO public.comments (id, created_at, email, message, name, post_id) VALUES (35, '14/14/2019', 'ada@das.om', 'This is great!', 'Tim', 7);
INSERT INTO public.pages (id, created_at, description, h1, href, short_description, title) VALUES (46, '2019-07-21 21:15:35.455000', '<div class="row justify-content-center mb-5 pb-3">
      <div class="col-md-7 heading-section ftco-animate text-center">
        <h2 class="mb-4">Our Services</h2>
        <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind
          texts.</p>
      </div>
    </div>
    <div class="row">
      <div class="col-md-4 ftco-animate">
        <div class="media d-block text-center block-6 services">
          <div class="icon d-flex justify-content-center align-items-center mb-5">
            <span class="flaticon-diet"></span>
          </div>
          <div class="media-body">
            <h3 class="heading">Healthy Foods</h3>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
          </div>
        </div>
      </div>
      <div class="col-md-4 ftco-animate">
        <div class="media d-block text-center block-6 services">
          <div class="icon d-flex justify-content-center align-items-center mb-5">
            <span class="flaticon-bicycle"></span>
          </div>
          <div class="media-body">
            <h3 class="heading">Fastest Delivery</h3>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
          </div>
        </div>
      </div>
      <div class="col-md-4 ftco-animate">
        <div class="media d-block text-center block-6 services">
          <div class="icon d-flex justify-content-center align-items-center mb-5"><span class="flaticon-pizza-1"></span>
          </div>
          <div class="media-body">
            <h3 class="heading">Original Recipes</h3>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic.</p>
          </div>
        </div>
      </div>
    </div>', 'OUR SERVICES', 'services', 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.', 'Services');
INSERT INTO public.categories (id, created_at, description, name, sort) VALUES (3, '09/11/2019', null, 'Burgers', 3);
INSERT INTO public.categories (id, created_at, description, name, sort) VALUES (1, '09/11/2019', '', 'Pizza', 1);
INSERT INTO public.categories (id, created_at, description, name, sort) VALUES (2, '09/11/2019', '', 'Drinks', 2);
INSERT INTO public.categories (id, created_at, description, name, sort) VALUES (4, '10/07/2019', '', 'Pasta', 4);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (12, '02/06/2019', null, true, 'http://localhost:8080/product-service/images/pizza-5.jpg', 'Tomatoe Pie', 3, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 4);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (8, '02/06/2019', null, true, 'http://localhost:8080/product-service/images/pizza-1.jpg', 'Italian Pizza', 2, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 1);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (54, '09/11/2019', null, false, 'http://localhost:8080/product-service/images/pizza-5.jpg', 'Pizza Cheese', 10, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 1);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (13, '02/06/2019', null, true, 'http://localhost:8080/product-service/images/pizza-6.jpg', 'Margherita', 2.2, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 3);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (10, '02/06/2019', null, true, 'http://localhost:8080/product-service/images/pizza-3.jpg', 'Caucasian Pizza', 6, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 3);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (11, '02/06/2019', null, true, 'http://localhost:8080/product-service/images/pizza-4.jpg', 'American Pizza', 4, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 1);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (56, '10/07/2019', null, false, 'http://localhost:8080/product-service/images/pizza-2.jpg', 'Pasta', 8, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 2);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (55, '10/07/2019', null, false, 'http://localhost:8080/product-service/images/pizza-3.jpg', 'Best Pizza', 12, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', 2);
INSERT INTO public.products (id, created_at, description, hot, image, name, price, short_description, category_id) VALUES (9, '02/06/2019', null, true, 'http://localhost:8080/product-service/images/pizza-2.jpg', 'Greek Pizza', 3, 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia', null);