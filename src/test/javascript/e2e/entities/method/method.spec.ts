/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MethodComponentsPage, MethodDeleteDialog, MethodUpdatePage } from './method.page-object';

const expect = chai.expect;

describe('Method e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let methodUpdatePage: MethodUpdatePage;
    let methodComponentsPage: MethodComponentsPage;
    let methodDeleteDialog: MethodDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Methods', async () => {
        await navBarPage.goToEntity('method');
        methodComponentsPage = new MethodComponentsPage();
        await browser.wait(ec.visibilityOf(methodComponentsPage.title), 5000);
        expect(await methodComponentsPage.getTitle()).to.eq('ceApp.method.home.title');
    });

    it('should load create Method page', async () => {
        await methodComponentsPage.clickOnCreateButton();
        methodUpdatePage = new MethodUpdatePage();
        expect(await methodUpdatePage.getPageTitle()).to.eq('ceApp.method.home.createOrEditLabel');
        await methodUpdatePage.cancel();
    });

    it('should create and save Methods', async () => {
        const nbButtonsBeforeCreate = await methodComponentsPage.countDeleteButtons();

        await methodComponentsPage.clickOnCreateButton();
        await promise.all([
            methodUpdatePage.setMethodNameInput('methodName'),
            methodUpdatePage.setFrequencyInput('5'),
            methodUpdatePage.setInjectionTypeInput('injectionType'),
            methodUpdatePage.setInjectionTimeInput('5'),
            methodUpdatePage.setMeasureValueInput('5'),
            methodUpdatePage.setUnitOfMeasureInput('unitOfMeasure'),
            methodUpdatePage.setExperimentTimeInput('5'),
            methodUpdatePage.setCurrentInput('5'),
            methodUpdatePage.capillaryTypeSelectLastOption(),
            methodUpdatePage.matrixListSelectLastOption()
        ]);
        expect(await methodUpdatePage.getMethodNameInput()).to.eq('methodName');
        expect(await methodUpdatePage.getFrequencyInput()).to.eq('5');
        expect(await methodUpdatePage.getInjectionTypeInput()).to.eq('injectionType');
        expect(await methodUpdatePage.getInjectionTimeInput()).to.eq('5');
        expect(await methodUpdatePage.getMeasureValueInput()).to.eq('5');
        expect(await methodUpdatePage.getUnitOfMeasureInput()).to.eq('unitOfMeasure');
        expect(await methodUpdatePage.getExperimentTimeInput()).to.eq('5');
        expect(await methodUpdatePage.getCurrentInput()).to.eq('5');
        await methodUpdatePage.save();
        expect(await methodUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await methodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Method', async () => {
        const nbButtonsBeforeDelete = await methodComponentsPage.countDeleteButtons();
        await methodComponentsPage.clickOnLastDeleteButton();

        methodDeleteDialog = new MethodDeleteDialog();
        expect(await methodDeleteDialog.getDialogTitle()).to.eq('ceApp.method.delete.question');
        await methodDeleteDialog.clickOnConfirmButton();

        expect(await methodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
