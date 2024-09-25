DELETE from user_roles;
DELETE from `role`;

ALTER TABLE user_roles DROP FOREIGN KEY fk_userol_on_role;

ALTER TABLE `role`
    MODIFY id BIGINT AUTO_INCREMENT;

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

INSERT INTO role (name, description, created_at, updated_at) 
VALUES 
('ADMIN', 'Função de administrador com acesso total', NOW(), NOW()),
('PROJECT_MANAGER', 'Função responsável por gerenciar projetos', NOW(), NOW()),
('PRODUCT_OWNER', 'Função responsável pelo backlog do produto e requisitos', NOW(), NOW());
