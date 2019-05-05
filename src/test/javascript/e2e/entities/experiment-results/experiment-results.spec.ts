/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ExperimentResultsComponentsPage,
    ExperimentResultsDeleteDialog,
    ExperimentResultsUpdatePage
} from './experiment-results.page-object';

const expect = chai.expect;

describe('ExperimentResults e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let experimentResultsUpdatePage: ExperimentResultsUpdatePage;
    let experimentResultsComponentsPage: ExperimentResultsComponentsPage;
    let experimentResultsDeleteDialog: ExperimentResultsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ExperimentResults', async () => {
        await navBarPage.goToEntity('experiment-results');
        experimentResultsComponentsPage = new ExperimentResultsComponentsPage();
        await browser.wait(ec.visibilityOf(experimentResultsComponentsPage.title), 5000);
        expect(await experimentResultsComponentsPage.getTitle()).to.eq('ceApp.experimentResults.home.title');
    });

    it('should load create ExperimentResults page', async () => {
        await experimentResultsComponentsPage.clickOnCreateButton();
        experimentResultsUpdatePage = new ExperimentResultsUpdatePage();
        expect(await experimentResultsUpdatePage.getPageTitle()).to.eq('ceApp.experimentResults.home.createOrEditLabel');
        await experimentResultsUpdatePage.cancel();
    });

    it('should create and save ExperimentResults', async () => {
        const nbButtonsBeforeCreate = await experimentResultsComponentsPage.countDeleteButtons();

        await experimentResultsComponentsPage.clickOnCreateButton();
        await promise.all([
            experimentResultsUpdatePage.setMeasuringPointInput('5'),
            experimentResultsUpdatePage.setVoltageValueInput('5'),
            experimentResultsUpdatePage.experimentSelectLastOption()
        ]);
        expect(await experimentResultsUpdatePage.getMeasuringPointInput()).to.eq('5');
        expect(await experimentResultsUpdatePage.getVoltageValueInput()).to.eq('5');
        await experimentResultsUpdatePage.save();
        expect(await experimentResultsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await experimentResultsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ExperimentResults', async () => {
        const nbButtonsBeforeDelete = await experimentResultsComponentsPage.countDeleteButtons();
        await experimentResultsComponentsPage.clickOnLastDeleteButton();

        experimentResultsDeleteDialog = new ExperimentResultsDeleteDialog();
        expect(await experimentResultsDeleteDialog.getDialogTitle()).to.eq('ceApp.experimentResults.delete.question');
        await experimentResultsDeleteDialog.clickOnConfirmButton();

        expect(await experimentResultsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
