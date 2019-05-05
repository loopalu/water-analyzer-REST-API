import { element, by, ElementFinder } from 'protractor';

export class BGECompositionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-bge-composition div table .btn-danger'));
    title = element.all(by.css('jhi-bge-composition div h2#page-heading span')).first();

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

export class BGECompositionUpdatePage {
    pageTitle = element(by.id('jhi-bge-composition-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    backgroundElectrolyteInput = element(by.id('field_backgroundElectrolyte'));
    bgeConcentrationInput = element(by.id('field_bgeConcentration'));
    methodSelect = element(by.id('field_method'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setBackgroundElectrolyteInput(backgroundElectrolyte) {
        await this.backgroundElectrolyteInput.sendKeys(backgroundElectrolyte);
    }

    async getBackgroundElectrolyteInput() {
        return this.backgroundElectrolyteInput.getAttribute('value');
    }

    async setBgeConcentrationInput(bgeConcentration) {
        await this.bgeConcentrationInput.sendKeys(bgeConcentration);
    }

    async getBgeConcentrationInput() {
        return this.bgeConcentrationInput.getAttribute('value');
    }

    async methodSelectLastOption() {
        await this.methodSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async methodSelectOption(option) {
        await this.methodSelect.sendKeys(option);
    }

    getMethodSelect(): ElementFinder {
        return this.methodSelect;
    }

    async getMethodSelectedOption() {
        return this.methodSelect.element(by.css('option:checked')).getText();
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

export class BGECompositionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-bGEComposition-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-bGEComposition'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
