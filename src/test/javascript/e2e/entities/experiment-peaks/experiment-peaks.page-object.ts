import { element, by, ElementFinder } from 'protractor';

export class ExperimentPeaksComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-experiment-peaks div table .btn-danger'));
    title = element.all(by.css('jhi-experiment-peaks div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ExperimentPeaksUpdatePage {
    pageTitle = element(by.id('jhi-experiment-peaks-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    peakNumberInput = element(by.id('field_peakNumber'));
    peakStartInput = element(by.id('field_peakStart'));
    peakEndInput = element(by.id('field_peakEnd'));
    peakHighestInput = element(by.id('field_peakHighest'));
    peakAreaInput = element(by.id('field_peakArea'));
    analyteConcentrationInput = element(by.id('field_analyteConcentration'));
    analyteSelect = element(by.id('field_analyte'));
    experimentAnalysisSelect = element(by.id('field_experimentAnalysis'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPeakNumberInput(peakNumber) {
        await this.peakNumberInput.sendKeys(peakNumber);
    }

    async getPeakNumberInput() {
        return this.peakNumberInput.getAttribute('value');
    }

    async setPeakStartInput(peakStart) {
        await this.peakStartInput.sendKeys(peakStart);
    }

    async getPeakStartInput() {
        return this.peakStartInput.getAttribute('value');
    }

    async setPeakEndInput(peakEnd) {
        await this.peakEndInput.sendKeys(peakEnd);
    }

    async getPeakEndInput() {
        return this.peakEndInput.getAttribute('value');
    }

    async setPeakHighestInput(peakHighest) {
        await this.peakHighestInput.sendKeys(peakHighest);
    }

    async getPeakHighestInput() {
        return this.peakHighestInput.getAttribute('value');
    }

    async setPeakAreaInput(peakArea) {
        await this.peakAreaInput.sendKeys(peakArea);
    }

    async getPeakAreaInput() {
        return this.peakAreaInput.getAttribute('value');
    }

    async setAnalyteConcentrationInput(analyteConcentration) {
        await this.analyteConcentrationInput.sendKeys(analyteConcentration);
    }

    async getAnalyteConcentrationInput() {
        return this.analyteConcentrationInput.getAttribute('value');
    }

    async analyteSelectLastOption() {
        await this.analyteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async analyteSelectOption(option) {
        await this.analyteSelect.sendKeys(option);
    }

    getAnalyteSelect(): ElementFinder {
        return this.analyteSelect;
    }

    async getAnalyteSelectedOption() {
        return this.analyteSelect.element(by.css('option:checked')).getText();
    }

    async experimentAnalysisSelectLastOption() {
        await this.experimentAnalysisSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async experimentAnalysisSelectOption(option) {
        await this.experimentAnalysisSelect.sendKeys(option);
    }

    getExperimentAnalysisSelect(): ElementFinder {
        return this.experimentAnalysisSelect;
    }

    async getExperimentAnalysisSelectedOption() {
        return this.experimentAnalysisSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ExperimentPeaksDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-experimentPeaks-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-experimentPeaks'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
