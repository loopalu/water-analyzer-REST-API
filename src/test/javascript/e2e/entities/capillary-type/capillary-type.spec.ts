/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CapillaryTypeComponentsPage, CapillaryTypeDeleteDialog, CapillaryTypeUpdatePage } from './capillary-type.page-object';

const expect = chai.expect;

describe('CapillaryType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let capillaryTypeUpdatePage: CapillaryTypeUpdatePage;
    let capillaryTypeComponentsPage: CapillaryTypeComponentsPage;
    let capillaryTypeDeleteDialog: CapillaryTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load CapillaryTypes', async () => {
        await navBarPage.goToEntity('capillary-type');
        capillaryTypeComponentsPage = new CapillaryTypeComponentsPage();
        await browser.wait(ec.visibilityOf(capillaryTypeComponentsPage.title), 5000);
        expect(await capillaryTypeComponentsPage.getTitle()).to.eq('ceApp.capillaryType.home.title');
    });

    it('should load create CapillaryType page', async () => {
        await capillaryTypeComponentsPage.clickOnCreateButton();
        capillaryTypeUpdatePage = new CapillaryTypeUpdatePage();
        expect(await capillaryTypeUpdatePage.getPageTitle()).to.eq('ceApp.capillaryType.home.createOrEditLabel');
        await capillaryTypeUpdatePage.cancel();
    });

    it('should create and save CapillaryTypes', async () => {
        const nbButtonsBeforeCreate = await capillaryTypeComponentsPage.countDeleteButtons();

        await capillaryTypeComponentsPage.clickOnCreateButton();
        await promise.all([
            capillaryTypeUpdatePage.setCapillaryNameInput('capillaryName'),
            capillaryTypeUpdatePage.setCapillaryLengthInput('5'),
            capillaryTypeUpdatePage.setEffectiveLengthInput('5'),
            capillaryTypeUpdatePage.setInnerDiameterInput('5'),
            capillaryTypeUpdatePage.setOuterDiameterInput('5'),
            capillaryTypeUpdatePage.setElectricForceInput('5')
        ]);
        expect(await capillaryTypeUpdatePage.getCapillaryNameInput()).to.eq('capillaryName');
        expect(await capillaryTypeUpdatePage.getCapillaryLengthInput()).to.eq('5');
        expect(await capillaryTypeUpdatePage.getEffectiveLengthInput()).to.eq('5');
        expect(await capillaryTypeUpdatePage.getInnerDiameterInput()).to.eq('5');
        expect(await capillaryTypeUpdatePage.getOuterDiameterInput()).to.eq('5');
        expect(await capillaryTypeUpdatePage.getElectricForceInput()).to.eq('5');
        await capillaryTypeUpdatePage.save();
        expect(await capillaryTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await capillaryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last CapillaryType', async () => {
        const nbButtonsBeforeDelete = await capillaryTypeComponentsPage.countDeleteButtons();
        await capillaryTypeComponentsPage.clickOnLastDeleteButton();

        capillaryTypeDeleteDialog = new CapillaryTypeDeleteDialog();
        expect(await capillaryTypeDeleteDialog.getDialogTitle()).to.eq('ceApp.capillaryType.delete.question');
        await capillaryTypeDeleteDialog.clickOnConfirmButton();

        expect(await capillaryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
