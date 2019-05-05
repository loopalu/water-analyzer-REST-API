/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnalyteComponentsPage, AnalyteDeleteDialog, AnalyteUpdatePage } from './analyte.page-object';

const expect = chai.expect;

describe('Analyte e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let analyteUpdatePage: AnalyteUpdatePage;
    let analyteComponentsPage: AnalyteComponentsPage;
    let analyteDeleteDialog: AnalyteDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Analytes', async () => {
        await navBarPage.goToEntity('analyte');
        analyteComponentsPage = new AnalyteComponentsPage();
        await browser.wait(ec.visibilityOf(analyteComponentsPage.title), 5000);
        expect(await analyteComponentsPage.getTitle()).to.eq('ceApp.analyte.home.title');
    });

    it('should load create Analyte page', async () => {
        await analyteComponentsPage.clickOnCreateButton();
        analyteUpdatePage = new AnalyteUpdatePage();
        expect(await analyteUpdatePage.getPageTitle()).to.eq('ceApp.analyte.home.createOrEditLabel');
        await analyteUpdatePage.cancel();
    });

    it('should create and save Analytes', async () => {
        const nbButtonsBeforeCreate = await analyteComponentsPage.countDeleteButtons();

        await analyteComponentsPage.clickOnCreateButton();
        await promise.all([analyteUpdatePage.setAnalyteNameInput('analyteName'), analyteUpdatePage.setAnalyteGroupInput('analyteGroup')]);
        expect(await analyteUpdatePage.getAnalyteNameInput()).to.eq('analyteName');
        expect(await analyteUpdatePage.getAnalyteGroupInput()).to.eq('analyteGroup');
        await analyteUpdatePage.save();
        expect(await analyteUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await analyteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Analyte', async () => {
        const nbButtonsBeforeDelete = await analyteComponentsPage.countDeleteButtons();
        await analyteComponentsPage.clickOnLastDeleteButton();

        analyteDeleteDialog = new AnalyteDeleteDialog();
        expect(await analyteDeleteDialog.getDialogTitle()).to.eq('ceApp.analyte.delete.question');
        await analyteDeleteDialog.clickOnConfirmButton();

        expect(await analyteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
