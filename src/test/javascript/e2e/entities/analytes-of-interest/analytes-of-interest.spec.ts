/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    AnalytesOfInterestComponentsPage,
    AnalytesOfInterestDeleteDialog,
    AnalytesOfInterestUpdatePage
} from './analytes-of-interest.page-object';

const expect = chai.expect;

describe('AnalytesOfInterest e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let analytesOfInterestUpdatePage: AnalytesOfInterestUpdatePage;
    let analytesOfInterestComponentsPage: AnalytesOfInterestComponentsPage;
    let analytesOfInterestDeleteDialog: AnalytesOfInterestDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AnalytesOfInterests', async () => {
        await navBarPage.goToEntity('analytes-of-interest');
        analytesOfInterestComponentsPage = new AnalytesOfInterestComponentsPage();
        await browser.wait(ec.visibilityOf(analytesOfInterestComponentsPage.title), 5000);
        expect(await analytesOfInterestComponentsPage.getTitle()).to.eq('ceApp.analytesOfInterest.home.title');
    });

    it('should load create AnalytesOfInterest page', async () => {
        await analytesOfInterestComponentsPage.clickOnCreateButton();
        analytesOfInterestUpdatePage = new AnalytesOfInterestUpdatePage();
        expect(await analytesOfInterestUpdatePage.getPageTitle()).to.eq('ceApp.analytesOfInterest.home.createOrEditLabel');
        await analytesOfInterestUpdatePage.cancel();
    });

    it('should create and save AnalytesOfInterests', async () => {
        const nbButtonsBeforeCreate = await analytesOfInterestComponentsPage.countDeleteButtons();

        await analytesOfInterestComponentsPage.clickOnCreateButton();
        await promise.all([analytesOfInterestUpdatePage.methodSelectLastOption(), analytesOfInterestUpdatePage.analyteSelectLastOption()]);
        await analytesOfInterestUpdatePage.save();
        expect(await analytesOfInterestUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await analytesOfInterestComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AnalytesOfInterest', async () => {
        const nbButtonsBeforeDelete = await analytesOfInterestComponentsPage.countDeleteButtons();
        await analytesOfInterestComponentsPage.clickOnLastDeleteButton();

        analytesOfInterestDeleteDialog = new AnalytesOfInterestDeleteDialog();
        expect(await analytesOfInterestDeleteDialog.getDialogTitle()).to.eq('ceApp.analytesOfInterest.delete.question');
        await analytesOfInterestDeleteDialog.clickOnConfirmButton();

        expect(await analytesOfInterestComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
