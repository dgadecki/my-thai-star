package com.devonfw.application.mtsj.usermanagement.logic.impl;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.devonfw.application.mtsj.general.common.base.QrCodeService;
import com.devonfw.application.mtsj.usermanagement.common.api.to.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import com.devonfw.application.mtsj.general.logic.base.AbstractComponentFacade;
import com.devonfw.application.mtsj.usermanagement.dataaccess.api.UserEntity;
import com.devonfw.application.mtsj.usermanagement.dataaccess.api.UserRoleEntity;
import com.devonfw.application.mtsj.usermanagement.dataaccess.api.repo.UserRepository;
import com.devonfw.application.mtsj.usermanagement.dataaccess.api.repo.UserRoleRepository;
import com.devonfw.application.mtsj.usermanagement.logic.api.Usermanagement;

/**
 * Implementation of component interface of usermanagement
 */
@Named
@Transactional
public class UsermanagementImpl extends AbstractComponentFacade implements Usermanagement {

    /**
     * Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UsermanagementImpl.class);

    /**
     * @see #getUserDao()
     */
    @Inject
    private UserRepository userDao;

    /**
     * @see #getUserRoleDao()
     */
    @Inject
    private UserRoleRepository userRoleDao;

    /**
     * The constructor.
     */
    public UsermanagementImpl() {

        super();
    }

    @Override
    public UserEto findUser(Long id) {

        LOG.debug("Get User with id {} from database.", id);
        UserEntity entity = getUserDao().find(id);
        UserEto eto = new UserEto();
        eto.setUsername(entity.getUsername());
        eto.setUserRoleId(entity.getUserRoleId());
        eto.setEmail(entity.getEmail());
        eto.setTwoFactorStatus(entity.isUsingTwoFactor());
        eto.setUserRoleId(entity.getUserRoleId());
        return eto;
    }

    @Override
    public UserQrCodeTo generateUserQrCode(String username) {

        LOG.debug("Get User with username {} from database.", username);
        final UserEntity user = getUserDao().findByUsername(username);
        if (user != null && user.isUsingTwoFactor()) {

            return QrCodeService.generateQrCode(user);
        } else {
            return null;
        }
    }

    @Override
    public Page<UserEto> findUserEtos(UserSearchCriteriaTo criteria) {

        Page<UserEntity> users = getUserDao().findUsers(criteria);
        return mapPaginatedEntityList(users, UserEto.class);
    }

    @Override
    public boolean deleteUser(Long userId) {

        UserEntity user = getUserDao().find(userId);
        getUserDao().delete(user);
        LOG.debug("The user with id '{}' has been deleted.", userId);
        return true;
    }

    @Override
    public UserEto saveUser(UserEto user) {

        Objects.requireNonNull(user, "user");
        UserEntity userEntity = getBeanMapper().map(user, UserEntity.class);

        // initialize, validate userEntity here if necessary
        UserEntity resultEntity = getUserDao().save(userEntity);
        LOG.debug("User with id '{}' has been created.", resultEntity.getId());

        return getBeanMapper().map(resultEntity, UserEto.class);
    }

    /**
     * Returns the field 'userDao'.
     *
     * @return the {@link UserDao} instance.
     */
    public UserRepository getUserDao() {

        return this.userDao;
    }

    @Override
    public UserRoleEto findUserRole(Long id) {

        LOG.debug("Get UserRole with id {} from database.", id);
        return getBeanMapper().map(getUserRoleDao().find(id), UserRoleEto.class);
    }

    @Override
    public Page<UserRoleEto> findUserRoleEtos(UserRoleSearchCriteriaTo criteria) {

        Page<UserRoleEntity> userroles = getUserRoleDao().findUserRoles(criteria);
        return mapPaginatedEntityList(userroles, UserRoleEto.class);
    }

    @Override
    public boolean deleteUserRole(Long userRoleId) {

        UserRoleEntity userRole = getUserRoleDao().find(userRoleId);
        getUserRoleDao().delete(userRole);
        LOG.debug("The userRole with id '{}' has been deleted.", userRoleId);
        return true;
    }

    @Override
    public UserRoleEto saveUserRole(UserRoleEto userRole) {

        Objects.requireNonNull(userRole, "userRole");
        UserRoleEntity userRoleEntity = getBeanMapper().map(userRole, UserRoleEntity.class);

        // initialize, validate userRoleEntity here if necessary
        UserRoleEntity resultEntity = getUserRoleDao().save(userRoleEntity);
        LOG.debug("UserRole with id '{}' has been created.", resultEntity.getId());

        return getBeanMapper().map(resultEntity, UserRoleEto.class);
    }

    /**
     * Returns the field 'userRoleDao'.
     *
     * @return the {@link UserRoleDao} instance.
     */
    public UserRoleRepository getUserRoleDao() {

        return this.userRoleDao;
    }

}
