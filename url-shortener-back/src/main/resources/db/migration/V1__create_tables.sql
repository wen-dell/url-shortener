CREATE SEQUENCE user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE user_url_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


CREATE TABLE public.user
(
    id bigint NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to postgres;


CREATE TABLE public.user_url
(
    id bigint NOT NULL DEFAULT nextval('user_url_id_seq'::regclass),
    original_url character varying COLLATE pg_catalog."default" NOT NULL,
    generated_url character varying COLLATE pg_catalog."default" NOT NULL,
    date_generation timestamp without time zone NOT NULL,
    code character varying COLLATE pg_catalog."default" NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT user_url_pkey PRIMARY KEY (id),
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user_url"
    OWNER to postgres;



