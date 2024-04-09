/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import GetAppIcon from '@material-ui/icons/GetApp';
import { useState } from 'react';
import { UploadDocumentReportProps, UploadDocumentReportState } from './UploadDocumentReport.types';

const useUploadDocumentReportStyles = makeStyles((theme: Theme) => ({
  report: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing(2),
  },
  message: {
    color: theme.palette.text.secondary,
  },
}));

export const UploadDocumentReport = ({ uploadedDocument }: UploadDocumentReportProps) => {
  const [state, setState] = useState<UploadDocumentReportState>({
    downloaded: false,
  });

  const onDownloadReport = () => {
    if (uploadedDocument) {
      const { report } = uploadedDocument;

      const fileName: string = 'upload-document-report.txt';
      const blob: Blob = new Blob([report], { type: 'text/plain' });
      const hyperlink: HTMLAnchorElement = document.createElement('a');
      hyperlink.setAttribute('download', fileName);
      hyperlink.setAttribute('href', window.URL.createObjectURL(blob));
      hyperlink.click();

      setState((prevState) => ({ ...prevState, downloaded: true }));
    }
  };

  const classes = useUploadDocumentReportStyles();

  if (!uploadedDocument) {
    return null;
  }

  return (
    <div className={classes.report}>
      <Typography variant="body1">The document has been successfully uploaded</Typography>

      {uploadedDocument?.report ? (
        <Button
          variant="outlined"
          size="small"
          disabled={state.downloaded}
          color="primary"
          type="button"
          form="upload-form-id"
          startIcon={<GetAppIcon />}
          data-testid="upload-document-download-report"
          onClick={() => onDownloadReport()}>
          Download report
        </Button>
      ) : null}
    </div>
  );
};
