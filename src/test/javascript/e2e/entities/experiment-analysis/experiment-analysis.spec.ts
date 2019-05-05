/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ExperimentAnalysisComponentsPage,
    ExperimentAnalysisDeleteDialog,
    ExperimentAnalysisUpdatePage
} from './experiment-analysis.page-object';

const expect = chai.expect;

describe('ExperimentAnalysis e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let experimentAnalysisUpdatePage: ExperimentAnalysisUpdatePage;
    let experimentAnalysisComponentsPage: ExperimentAnalysisComponentsPage;
    let experimentAnalysisDeleteDialog: ExperimentAnalysisDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ExperimentAnalyses', async () => {
        await navBarPage.goToEntity('experiment-analysis');
        experimentAnalysisComponentsPage = new ExperimentAnalysisComponentsPage();
        await browser.wait(ec.visibilityOf(experimentAnalysisComponentsPage.title), 5000);
        expect(await experimentAnalysisComponentsPage.getTitle()).to.eq('ceApp.experimentAnalysis.home.title');
    });

    it('should load create ExperimentAnalysis page', async () => {
        await experimentAnalysisComponentsPage.clickOnCreateButton();
        experimentAnalysisUpdatePage = new ExperimentAnalysisUpdatePage();
        expect(await experimentAnalysisUpdatePage.getPageTitle()).to.eq('ceApp.experimentAnalysis.home.createOrEditLabel');
        await experimentAnalysisUpdatePage.cancel();
    });

    it('should create and save ExperimentAnalyses', async () => {
        const nbButtonsBeforeCreate = await experimentAnalysisComponentsPage.countDeleteButtons();

        await experimentAnalysisComponentsPage.clickOnCreateButton();
        await promise.all([
            experimentAnalysisUpdatePage.setMeasuringPointInput('5'),
            experimentAnalysisUpdatePage.setVoltageExperimentInput('5'),
            experimentAnalysisUpdatePage.setVoltageSmoothedInput('5'),
            experimentAnalysisUpdatePage.setValueMovingAverageSubtractedInput('5'),
            experimentAnalysisUpdatePage.setValueOverThresholdInput('5'),
            experimentAnalysisUpdatePage.experimentSelectLastOption()
        ]);
        expect(await experimentAnalysisUpdatePage.getMeasuringPointInput()).to.eq('5');
        expect(await experimentAnalysisUpdatePage.getVoltageExperimentInput()).to.eq('5');
        expect(await experimentAnalysisUpdatePage.getVoltageSmoothedInput()).to.eq('5');
        expect(await experimentAnalysisUpdatePage.getValueMovingAverageSubtractedInput()).to.eq('5');
        expect(await experimentAnalysisUpdatePage.getValueOverThresholdInput()).to.eq('5');
        await experimentAnalysisUpdatePage.save();
        expect(await experimentAnalysisUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await experimentAnalysisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ExperimentAnalysis', async () => {
        const nbButtonsBeforeDelete = await experimentAnalysisComponentsPage.countDeleteButtons();
        await experimentAnalysisComponentsPage.clickOnLastDeleteButton();

        experimentAnalysisDeleteDialog = new ExperimentAnalysisDeleteDialog();
        expect(await experimentAnalysisDeleteDialog.getDialogTitle()).to.eq('ceApp.experimentAnalysis.delete.question');
        await experimentAnalysisDeleteDialog.clickOnConfirmButton();

        expect(await experimentAnalysisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
