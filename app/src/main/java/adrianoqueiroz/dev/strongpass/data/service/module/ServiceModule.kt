package adrianoqueiroz.dev.strongpass.data.service.module

import adrianoqueiroz.dev.strongpass.data.service.AccountService
import adrianoqueiroz.dev.strongpass.data.service.CategoryService
import adrianoqueiroz.dev.strongpass.data.service.PasswordService
import adrianoqueiroz.dev.strongpass.data.service.impl.AccountServiceImpl
import adrianoqueiroz.dev.strongpass.data.service.impl.CategoryServiceImpl
import adrianoqueiroz.dev.strongpass.data.service.impl.PasswordServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
   @Binds
   abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

   @Binds
   abstract fun provideCategoryService(impl: CategoryServiceImpl): CategoryService

   @Binds
   abstract fun providePasswordService(impl: PasswordServiceImpl): PasswordService
}