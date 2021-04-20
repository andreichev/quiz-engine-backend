INSERT INTO public.users (id, active, password, username) VALUES (1, true, '$2a$10$iXCEmcmtX2tGuN5S3650uehR0p8jZvAQIlRbnjBxS1mvuBdBxXoJG', 'admin');

INSERT INTO public.quiz_participant (id, name, quiz_id) VALUES (1, 'Михаил', 1);

INSERT INTO public.quiz (id, description, active, is_any_order, start_date, title, author_id) VALUES (1, '', true, true, '2020-09-09 00:00:00.000000', 'Викторина 1', 1);

INSERT INTO public.question_option (id, correct, text, question_id) VALUES (1, true, 'Lawyer', 1);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (2, false, 'Строитель', 1);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (3, false, 'Бухгалтер', 1);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (4, true, 'Нобелевская премия по физике', 2);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (5, false, 'Орден славы', 2);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (6, false, 'Золотой громофон', 2);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (7, false, 'Оскар', 2);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (8, true, 'Шони (Оклахома)', 3);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (9, false, 'rdssdf', 3);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (10, false, 'sdfsdfsdf', 3);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (11, false, 'dfgdfgdgdfg', 3);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (12, true, 'Multiple', 4);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (13, true, 'Пушкин, Александр Сергеевич', 5);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (14, false, 'Лермонтов', 5);
INSERT INTO public.question_option (id, correct, text, question_id) VALUES (15, false, 'Ломоносов', 5);

INSERT INTO public.question_answer (id, participant_id, question_id, option_id) VALUES (4, 1, 1, 1);
INSERT INTO public.question_answer (id, participant_id, question_id, option_id) VALUES (5, 1, 2, 6);
INSERT INTO public.question_answer (id, participant_id, question_id, option_id) VALUES (6, 1, 3, 8);

INSERT INTO public.question (id, text, quiz_id) VALUES (1, 'Кто, или что, или какой profession Ленин, Владимир Ильич?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (2, 'Какая награда присвоена Эйнштейн, Альберт?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (3, 'Кто, или что, или какой birth place Питт, Брэд?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (5, 'Кто, или что, или какой author Медный всадник (поэма)?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (4, 'Какая религия Храм всех религий?', 1);